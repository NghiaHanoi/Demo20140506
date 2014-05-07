package org.wiperdog.test
import static org.junit.Assert.*
import static org.ops4j.pax.exam.CoreOptions.*

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.ops4j.pax.exam.Configuration
import org.ops4j.pax.exam.Option
import org.ops4j.pax.exam.junit.PaxExam
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy
import org.ops4j.pax.exam.spi.reactors.PerClass
import org.quartz.InterruptableJob
import org.quartz.JobExecutionContext
import org.quartz.JobExecutionException
import org.quartz.JobKey
import org.quartz.JobListener
import org.quartz.ListenerManager
import org.quartz.Matcher
import org.quartz.Scheduler
import org.quartz.Trigger
import org.quartz.TriggerBuilder
import org.quartz.UnableToInterruptJobException
import org.quartz.impl.StdSchedulerFactory
import org.quartz.impl.matchers.KeyMatcher
import org.quartz.simpl.PropertySettingJobFactory

/**
 * @author nguyenvannghia
 *
 */
@RunWith(PaxExam.class)
@ExamReactorStrategy(PerClass.class)
class TestQuartzJob {
	public TestQuartzJob(){	
	}	
	private Scheduler sched;
	private TestInterruptableJob testJob;
	private Trigger trigger;
	public String jobCompleteRunning = "PENDING"
	public String jobStartRunning = "PENDING"
	private static final String JOB_DONE = "DONE"
	private static final String JOB_TOBE_EXECUTE = "EXECUTING"
	private static final Matcher<JobKey> NULLJOBMATCHER = KeyMatcher.keyEquals(new JobKey("NULLNAME", "NULLGROUP"));
	ClassLoader loader
	@Configuration
	public Option[] config() {
		return options(
		cleanCaches(true),
		frameworkStartLevel(6),
		systemProperty("felix.log.level").value("4"), // 4 = DEBUG
		mavenBundle("org.ops4j.pax.url", "pax-url-mvn", "1.3.7").startLevel(1),
		mavenBundle("biz.aQute.bnd", "bndlib", "2.1.0").startLevel(2),
		mavenBundle("org.ops4j.pax.url", "pax-url-wrap", "1.6.0").startLevel(2),
		mavenBundle("org.ops4j.pax.url", "pax-url-commons", "1.6.0").startLevel(2),
		mavenBundle("org.ops4j.pax.swissbox", "pax-swissbox-bnd", "1.7.0"),
		mavenBundle("org.ops4j.pax.swissbox", "pax-swissbox-property", "1.7.0"),
		mavenBundle("org.codehaus.groovy", "groovy-all", "2.2.1").startLevel(2),
		mavenBundle("commons-collections", "commons-collections", "3.2.1").startLevel(2),
		mavenBundle("commons-beanutils", "commons-beanutils", "1.8.0").startLevel(2),
		mavenBundle("commons-digester", "commons-digester", "2.0").startLevel(2),
		mavenBundle("org.quartz-scheduler", "quartz", "2.2.0").startLevel(3),
		wrappedBundle(mavenBundle("c3p0", "c3p0", "0.9.1.2").startLevel(3)),	
		junitBundles()
		);
	}
	
	@Before
	public void prepare() {		
		loader = Thread.currentThread().getContextClassLoader();
		sched = new StdSchedulerFactory().getScheduler()
		PropertySettingJobFactory jfactory = new PropertySettingJobFactory();
		jfactory.setWarnIfPropertyNotFound(false);
		sched.setJobFactory(jfactory);
		sched.start();
		trigger =  TriggerBuilder.newTrigger()
			.withIdentity("test-pax-exam-trigger")
			.startAt(new Date(System.currentTimeMillis()))
			.build();		
		ListenerManager lm = sched.getListenerManager();
		lm.addJobListener(new TestJobListener(), NULLJOBMATCHER);
	}

	@After
	public void finish() {
		sched.shutdown()
		sched = null
	}

	/**
	 * Assign job to job class already exists.
	 * Expected: job will be add to list assigned.
	 */
	@Test
	public void testJobDone() throws Exception {	
		 Thread.currentThread().setContextClassLoader(loader);	 		 
		 testJob = new TestInterruptableJob()
		 TriggerBuilder<? extends Trigger> builder = trigger.getTriggerBuilder();
		 Trigger newTrigger = builder.forJob(testJob).build();
		 if (sched.getTrigger(trigger.getKey()) != null) {
			 sched.rescheduleJob(trigger.getKey(), newTrigger);
		 } else {
			 sched.scheduleJob(newTrigger);
		 }
		 System.out.println("+=== SYSTEM SLEEP FOR 10s");
		 Thread.sleep(10000);
		 assertEquals(jobCompleteRunning, JOB_DONE);
		 
	}
	class TestInterruptableJob implements InterruptableJob{
		public void execute(JobExecutionContext arg0) throws JobExecutionException {
			System.out.println("This is test interrupt job");
		}
		public void interrupt() throws UnableToInterruptJobException {
			Thread.currentThread().interrupt();
			
		}
	}
	class TestJobListener implements JobListener {
	
		public String getName() {
			return "TEST-JOBLISTENER";
		}
	
		public void jobToBeExecuted(JobExecutionContext context) {			
			
		}
	
		public void jobExecutionVetoed(JobExecutionContext context) {			
			jobStartRunning = "EXECUTING"
		}
	
		public void jobWasExecuted(JobExecutionContext context,
				JobExecutionException jobException) {
			jobCompleteRunning = "DONE"
		}
		
	}
}
