package net.aionstudios.proteus.api.event;

public interface Callback {
	
	public static Callback NULL = new Callback() {

		@Override
		public void call() {
			// do nothing
		}
		
	};

	public void call();
	
}
