package net.trackmate.collection;

import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * A {@link List} that is a {@link RefCollection}. It provides variants of
 * {@link List} methods that take object references that can be used for
 * retrieval. Depending on concrete implementation, these object references can
 * be cleared, ignored or re-used.
 *
 * @param <O>
 *            the type of elements maintained by this list.
 *
 * @author Tobias Pietzsch &lt;tobias.pietzsch@gmail.com&gt;
 */
public interface RefList< O > extends RefCollection< O >, List< O >
{
	/**
	 * Returns the element at the specified position in this list.
	 *
	 * <p>
	 * This method is a (potentially) allocation-free version of
	 * {@link #get(int)}.
	 *
	 * @param index
	 *            index of the element to return.
	 * @param obj
	 *            an object reference that can be used for retrieval. Depending
	 *            on concrete implementation, this object can be cleared,
	 *            ignored or re-used.
	 * @return the element at the specified index. The object actually returned
	 *         might be the one specified as parameter, depending on concrete
	 *         implementation.
	 * @throws IndexOutOfBoundsException
	 *             if the index is out of range, i.e.,
	 *             <tt>index &lt; 0 || index &gt;= size()</tt>.
	 */
	public O get( final int index, final O obj );

	/**
	 * Removes the element at the specified position in this list (optional
	 * operation). Shifts any subsequent elements to the left (subtracts one
	 * from their indices). Returns the element that was removed from the list.
	 *
	 * <p>
	 * This method is a (potentially) allocation-free version of
	 * {@link #remove(Object)}.
	 *
	 * @param index
	 *            the index of the element to be removed.
	 * @param obj
	 *            an object reference that can be used for retrieval. Depending
	 *            on concrete implementation, this object can be cleared,
	 *            ignored or re-used.
	 * @return the element previously at the specified position. The object
	 *         actually returned might be the one specified as parameter,
	 *         depending on concrete implementation.
	 * @throws UnsupportedOperationException
	 *             if the remove operation is not supported by this list.
	 * @throws IndexOutOfBoundsException
	 *             if the index is out of range, i.e.,
	 *             <tt>index &lt; 0 || index &gt;= size()</tt>.
	 */
	public O remove( final int index, final O obj );

	/**
	 * Replaces the element at the specified position in this list with the
	 * specified element (optional operation).
	 *
	 * <p>
	 * This method is a (potentially) allocation-free version of
	 * {@link #set(int, Object)}.
	 *
	 * @param index
	 *            index of the element to replace.
	 * @param obj
	 *            element to be stored at the specified position.
	 * @param replacedObj
	 *            an object reference that can be used for retrieval. Depending
	 *            on concrete implementation, this object can be cleared,
	 *            ignored or re-used.
	 * @return the element previously at the specified position. The object
	 *         actually returned might be the one specified as parameter
	 *         {@code replacedObj}, depending on concrete implementation.
	 * @throws UnsupportedOperationException
	 *             if the set operation is not supported by this list.
	 * @throws ClassCastException
	 *             if the class of the specified element prevents it from being
	 *             added to this list.
	 * @throws NullPointerException
	 *             if the specified element is null and this list does not
	 *             permit null elements.
	 * @throws IllegalArgumentException
	 *             if some property of the specified element prevents it from
	 *             being added to this list.
	 * @throws IndexOutOfBoundsException
	 *             if the index is out of range, i.e.,
	 *             <tt>index &lt; 0 || index &gt;= size()</tt>.
	 */
	public O set( final int index, final O obj, final O replacedObj );

	/**
	 * Shuffle the elements of the list using the specified random number
	 * generator.
	 *
	 * @param rand
	 *            a random number generator.
	 */
	public void shuffle( Random rand );

	/**
	 * Sort the values in the list, in ascending order according to the
	 * specified {@link Comparator}.
	 *
	 * @param comparator
	 *            the comparator to use for ordering.
	 */
	@Override
	public void sort( Comparator< ? super O > comparator );

	/**
	 * Swaps the elements at the specified positions in this list.
	 *
	 * @param i
	 *            the index of one element to be swapped.
	 * @param j
	 *            the index of the other element to be swapped.
	 */
	public void swap( int i, int j );
}