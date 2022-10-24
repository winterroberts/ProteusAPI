package net.aionstudios.proteus.api;

import java.util.Set;

import net.aionstudios.proteus.routing.CompositeRouter;

public interface ProteusApp {
	
	public Set<CompositeRouter> build();

}
