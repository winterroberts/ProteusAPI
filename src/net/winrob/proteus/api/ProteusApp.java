package net.winrob.proteus.api;

import java.util.Set;

import net.winrob.proteus.routing.CompositeRouter;

public interface ProteusApp {
	
	public Set<CompositeRouter> build();

}
