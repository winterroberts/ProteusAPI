package net.aionstudios.proteus.api.event;

/**
 * A class intended to call the function within it after another task is completed.
 * 
 * @author Winter Roberts
 *
 */
public interface Callback {
	
	public static Callback NULL = new Callback() {

		@Override
		public void call() {
			// do nothing
		}
		
	};

	/**
	 * The method that will be run after the previous task, which accepts a {@link Callback}, is run.
	 */
	public void call();
	
}
