package net.trackmate.collection.ref;

import net.trackmate.Ref;
import net.trackmate.RefPool;
import net.trackmate.collection.RefStack;
import net.trackmate.pool.PoolObject;

/**
 * A stack implementation for {@link PoolObject}s entirely based on a
 * {@link RefArrayList}.
 *
 * @author Jean-Yves Tinevez
 *
 * @param <O>
 *            recursive type of the {@link PoolObject}s stored in this stack.
 */
// TODO rename RefArrayStack
public class RefArrayStack< O extends Ref< O > > extends RefArrayList< O > implements RefStack< O >
{

	/*
	 * CONSTRUCTOR
	 */

	/**
	 * Instantiates an empty stack for the specified pool with default capacity.
	 *
	 * @param pool
	 *            the pool to draw objects from in order to build this stack.
	 */
	public RefArrayStack( final RefPool< O > pool )
	{
		super( pool );
	}

	/**
	 * Instantiates an empty stack for the specified pool.
	 *
	 * @param pool
	 *            the pool to draw objects from in order to build this stack.
	 * @param initialCapacity
	 *            the initial capacity.
	 */
	public RefArrayStack( final RefPool< O > pool, final int initialCapacity )
	{
		super( pool, initialCapacity );
	}

	/*
	 * METHODS
	 */

	@Override
	public O peek()
	{
		return get( size() - 1 );
	}

	@Override
	public O peek( final O obj )
	{
		return get( size() - 1, obj );
	}

	@Override
	public O pop()
	{
		return remove( size() - 1 );
	}

	@Override
	public O pop( final O obj )
	{
		return remove( size() - 1, obj );
	}

	@Override
	public void push( final O obj )
	{
		add( obj );
	}

	@Override
	public int search( final Object obj )
	{
		if ( !( obj instanceof PoolObject ) ) { return -1; }
		@SuppressWarnings( "rawtypes" )
		final int value = ( ( PoolObject ) obj ).getInternalPoolIndex();
		final int index = getIndexCollection().lastIndexOf( value );
		if ( index < 0 )
		{
			return -1;
		}
		else
		{
			return size() - index;
		}
	}
}