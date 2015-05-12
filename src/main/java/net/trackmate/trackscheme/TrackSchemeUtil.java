package net.trackmate.trackscheme;

import java.util.Iterator;

import net.trackmate.graph.Edge;
import net.trackmate.graph.Graph;
import net.trackmate.graph.IntPoolObjectMap;
import net.trackmate.graph.Vertex;
import net.trackmate.graph.mempool.ByteMappedElement;

public class TrackSchemeUtil
{
	// TODO: maybe move and rename to TraackSchemeGraph.build()
	public static < V extends Vertex< E > & HasTimepoint, E extends Edge< V > >
	TrackSchemeGraph buildTrackSchemeGraph(
			final Graph< V, E > graph,
			final GraphIdBimap< V, E > idmap )
	{
		final int initialCapacity = 1024;

		final TrackSchemeGraph tsg = new TrackSchemeGraph( initialCapacity );
		final TrackSchemeVertex tsv = tsg.vertexRef();
		final TrackSchemeVertex tsv2 = tsg.vertexRef();
		final TrackSchemeEdge tse = tsg.edgeRef();

		final IntPoolObjectMap< TrackSchemeVertex, ByteMappedElement > idToTrackSchemeVertex =
				new IntPoolObjectMap< TrackSchemeVertex, ByteMappedElement >( tsg.getVertexPool(), initialCapacity );
		final Iterator< V > vi = graph.vertexIterator();
		while ( vi.hasNext() )
		{
			final V v = vi.next();
			final int id = idmap.getVertexId( v );
			final String label = Integer.toString( id );
			final int timepoint = v.getTimePoint();
			tsg.addVertex( tsv ).init( id, label, timepoint, false );
			idToTrackSchemeVertex.put( id, tsv );
		}

		final V v = graph.vertexRef();
		final Iterator< E > ei = graph.edgeIterator();
		while ( ei.hasNext() )
		{
			final E e = ei.next();
			idToTrackSchemeVertex.get( idmap.getVertexId( e.getSource( v ) ), tsv );
			idToTrackSchemeVertex.get( idmap.getVertexId( e.getTarget( v ) ), tsv2 );
			tsg.addEdge( tsv, tsv2, tse );
		}
		graph.releaseRef( v );

		tsg.releaseRef( tse );
		tsg.releaseRef( tsv );
		tsg.releaseRef( tsv2 );

		return tsg;
	}
}