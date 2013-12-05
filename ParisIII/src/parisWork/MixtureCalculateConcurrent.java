package parisWork;

import java.util.Vector;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.eclipse.swt.widgets.ProgressBar;

import parisInit.State;

public class MixtureCalculateConcurrent {

	/**
	 * @param args
	 */
	private State mState;
	private Vector<Mixture> mMixtures;
	private static int nprocs = Runtime.getRuntime().availableProcessors();
	private static int nTasks = 4*nprocs;
	private ExecutorService executor = null;
	private static Vector<Future<Void>> list = null;
	
	MixtureCalculateConcurrent(State activeState) {
		mState = activeState;
	}
	
	public void calculate(Vector<Mixture> mixtures) throws InterruptedException, ExecutionException {
		mMixtures = mixtures;
		
		executor = Executors.newFixedThreadPool(nprocs);
		list  = new Vector<Future<Void>>(nTasks);

		int stepsize = (mMixtures.size()+nTasks-1)/nTasks;
		int from = 0;
		int to = Math.min(from + stepsize, mMixtures.size());
		while (from < mMixtures.size()) {
			ConcurrentCalculate task = new ConcurrentCalculate(mState, mMixtures, from, to);
			list.add(executor.submit(task));
			from = to;
			to = Math.min(from + stepsize, mMixtures.size());
		}
		
		executor.shutdown();
		
		return;
	}
	
	public void updateProgress(ProgressBar progressBar, int previousMixtureCount, int totalCount) throws InterruptedException {
		double percent = 100.0/totalCount;
		int progress, previousProgress = 0;

		int cnt=0;
		while (!executor.awaitTermination(10, TimeUnit.MILLISECONDS) && cnt < list.size()) {
			cnt=0;
			for (int m=0; m<list.size(); m++) {
				if (list.get(m).isDone()) cnt++;
			}
			if (!progressBar.getDisplay().readAndDispatch()) {
				progress = (int)((previousMixtureCount+((double)cnt/list.size())* mMixtures.size()) * percent);
				if (progress != previousProgress) {
					progressBar.setSelection(progress);
					previousProgress = progress;
				}
			}
		}
	}

	/**
	 * This illustrate the use of the Callable task framework.
	 */
	public class ConcurrentCalculate implements Callable<Void> {

		/**
		 * 
		 */
		private static final long serialVersionUID = -5069206897899763709L;

		private Vector<Mixture> mMixtures;
		private State mState;
		protected int mFrom;
		protected int mTo;

		public ConcurrentCalculate(State activeState, Vector<Mixture> mixtures, int from, int to) {
			mState = activeState;
			mMixtures = mixtures;
			mFrom = from;
			mTo = to;
		}

		@Override
		public Void call() throws Exception {
			for (int i=mFrom; i<mTo; i++) {
				try {
					mMixtures.get(i).calculateMixtureScore(mState);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return null;
		}
	}
}
