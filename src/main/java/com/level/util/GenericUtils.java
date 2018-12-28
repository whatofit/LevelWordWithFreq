package com.level.util;

import java.util.Set;
import java.util.Map;
import java.util.List;
import java.util.Queue;
import java.util.HashSet;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 包含获得各种集合对象的常用方法的泛型工具类。
 * <p>
 * 如果要获得一个 {@code Map<String, String>} 的对象，可以通过下面的方式实现：
 * {@literal Map<String, String> map = GenericUtils.getMap();}
 * 。但是不能直接作为参数使用，例如有这样一个方法： {@literal setInfo(Map<String, String>)}，不能直接这样调用：<s>
 * <code>setInfo(GenericUtils.getMap())</code></s>
 * </p>
 * 
 * @author Fuchun
 * @version $Id: GenericUtils.java 4754 2011-03-26 19:50 fuchun $
 */
public class GenericUtils {

	/**
	 * 判断集合是否为空
	 */
	public static <T> boolean isEmpty(Collection<? extends T> c) {
		if (c == null || c.isEmpty()) {
			return true;
		}
		return false;
	}

	/**
	 * 判断集合是否不为空
	 */
	public static <T> boolean isNotEmpty(Collection<? extends T> c) {
		if (c != null && !c.isEmpty()) {
			return true;
		}
		return false;
	}

	/**
	 * 用该方法来代替 {@code new HashMap<K, V>()} 方式获得新的 {@code java.util.Map} 的实例对象。
	 * 
	 * @param <K>
	 *            {@code Map} 中的键对象。
	 * @param <V>
	 *            {@code Map} 中的值对象。
	 * @return 返回 {@code java.util.Map<K, V>} 关于 {@code java.util.HashMap<K, V>}
	 *         实现的新实例。
	 */
	public static <K, V> Map<K, V> getMap() {
		return new HashMap<K, V>();
	}

	/**
	 * 用该方法来代替 {@code new HashMap<K, V>(int)} 方式获得新的 {@code java.util.Map}
	 * 的实例对象。
	 * 
	 * @param <K>
	 *            {@code Map} 中的键对象。
	 * @param <V>
	 *            {@code Map} 中的值对象。
	 * @param initialCapacity
	 *            初始容量。
	 * @return 返回 {@code java.util.Map<K, V>} 关于 {@code java.util.HashMap<K, V>}
	 *         实现的新实例。
	 */
	public static <K, V> Map<K, V> getMap(int initialCapacity) {
		return new HashMap<K, V>(initialCapacity);
	}

	/**
	 * 用该方法来代替 {@code new ConcurrentHashMap<K, V>()} 方式获得新的
	 * {@code java.util.Map} 的实例对象。
	 * 
	 * @param <K>
	 *            {@code Map} 中的键对象。
	 * @param <V>
	 *            {@code Map} 中的值对象。
	 * @return 返回 {@code java.util.Map<K, V>} 关于
	 *         {@code java.util.concurrent.ConcurrentHashMap<K, V>} 实现的新实例。
	 */
	public static <K, V> Map<K, V> getConcurrentMap() {
		return new ConcurrentHashMap<K, V>();
	}

	/**
	 * 用该方法来代替 {@code new ConcurrentHashMap<K, V>(int)} 方式获得新的
	 * {@code java.util.Map} 的实例对象。
	 * 
	 * @param <K>
	 *            {@code Map} 中的键对象。
	 * @param <V>
	 *            {@code Map} 中的值对象。
	 * @param initialCapacity
	 *            初始容量。
	 * @return 返回 {@code java.util.Map<K, V>} 关于
	 *         {@code java.util.concurrent.ConcurrentHashMap<K, V>} 实现的新实例。
	 */
	public static <K, V> Map<K, V> getConcurrentMap(int initialCapacity) {
		return new ConcurrentHashMap<K, V>(initialCapacity);
	}

	/**
	 * 用该方法来代替 {@code new LinkedHashMap<K, V>()} 方式获得新的 {@code java.util.Map}
	 * 的实例对象。
	 * 
	 * @param <K>
	 *            {@code Map} 中的键对象。
	 * @param <V>
	 *            {@code Map} 中的值对象。
	 * @return 返回 {@code java.util.Map<K, V>} 关于
	 *         {@code java.util.LinkedHashMap<K, V>} 实现的新实例。
	 */
	public static <K, V> Map<K, V> getLinkedMap() {
		return new LinkedHashMap<K, V>();
	}

	/**
	 * 用该方法来代替 {@code new LinkedHashMap<K, V>(int)} 方式获得新的 {@code java.util.Map}
	 * 的实例对象。
	 * 
	 * @param <K>
	 *            {@code Map} 中的键对象。
	 * @param <V>
	 *            {@code Map} 中的值对象。
	 * @param initialCapacity
	 *            初始容量。
	 * @return 返回 {@code java.util.Map<K, V>} 关于
	 *         {@code java.util.LinkedHashMap<K, V>} 实现的新实例。
	 */
	public static <K, V> Map<K, V> getLinkedMap(int initialCapacity) {
		return new LinkedHashMap<K, V>(initialCapacity);
	}

	/**
	 * 用该方法来代替 {@code new TreeMap<K, V>()} 方式获得新的 {@code java.util.Map} 的实例对象。
	 * 
	 * @param <K>
	 *            {@code Map} 中的键对象。
	 * @param <V>
	 *            {@code Map} 中的值对象。
	 * @return 返回 {@code java.util.Map<K, V>} 关于 {@code java.util.TreeMap<K, V>}
	 *         实现的新实例。
	 */
	public static <K, V> Map<K, V> getTreeMap() {
		return new TreeMap<K, V>();
	}

	/**
	 * 用该方法来代替 {@code new ConcurrentHashMap<K, V>()} 方式获得新的
	 * {@code java.util.concurrent.ConcurrentHashMap} 的实例对象。
	 * 
	 * @param <K>
	 *            {@code Map} 中的键对象。
	 * @param <V>
	 *            {@code Map} 中的值对象。
	 * @return 返回 {@code java.util.concurrent.ConcurrentMap<K, V>} 关于
	 *         {@code java.util.concurrent.ConcurrentHashMap<K, V>} 实现的新实例。
	 */
	public static <K, V> ConcurrentMap<K, V> getConcurrentHashMap() {
		return new ConcurrentHashMap<K, V>();
	}

	/**
	 * 用该方法来代替 {@code new ConcurrentHashMap<K, V>(int)} 方式获得新的
	 * {@code java.util.concurrent.ConcurrentHashMap} 的实例对象。
	 * 
	 * @param <K>
	 *            {@code Map} 中的键对象。
	 * @param <V>
	 *            {@code Map} 中的值对象。
	 * @param initialCapacity
	 *            初始容量。
	 * @return 返回 {@code java.util.concurrent.ConcurrentMap<K, V>} 关于
	 *         {@code java.util.concurrent.ConcurrentHashMap<K, V>} 实现的新实例。
	 */
	public static <K, V> ConcurrentMap<K, V> getConcurrentHashMap(
			int initialCapacity) {
		return new ConcurrentHashMap<K, V>(initialCapacity);
	}

	/**
	 * 用该方法来代替 {@code new ArrayList<T>()} 方式获得新的 {@code java.util.List} 的实例对象。
	 * 
	 * @param <T>
	 *            {@code List<T>} 中保存的对象。
	 * @return 返回 {@code java.util.List<T>} 关于 {@code java.util.ArrayList<T>}
	 *         实现的新实例。
	 */
	public static <T> List<T> getList() {
		return new ArrayList<T>();
	}

	/**
	 * 用该方法来代替 {@code new ArrayList<T>(int)} 方式获得新的 {@code java.util.List}
	 * 的实例对象。
	 * 
	 * @param <T>
	 *            {@code List<T>} 中保存的对象。
	 * @param initialCapacity
	 *            列表的初始容量。
	 * @return 返回 {@code java.util.List<T>} 关于 {@code java.util.ArrayList<T>}
	 *         实现的新实例。
	 */
	public static <T> List<T> getList(int initialCapacity) {
		return new ArrayList<T>(initialCapacity);
	}

	/**
	 * 用该方法来代替 {@code new ArrayList<T>()} 方式获得新的 {@code java.util.List} 的实例对象。
	 * 
	 * @param <T>
	 *            {@code List<T>} 中保存的对象。
	 * @param c
	 *            其中的元素将存放在新的 {@code list} 中的 {@code collection}。
	 * @return 返回 {@code java.util.List<T>} 关于 {@code java.util.ArrayList<T>}
	 *         实现的新实例。
	 */
	public static <T> List<T> getList(Collection<? extends T> c) {
		if (isNotEmpty(c))
			return new ArrayList<T>(c);
		return new ArrayList<T>();
	}

	/**
	 * 用该方法来代替 {@code new LinkedList<T>()} 方式获得新的 {@code java.util.List} 的实例对象。
	 * 
	 * @param <T>
	 *            {@code List<T>} 中保存的对象。
	 * @return 返回 {@code java.util.List<T>} 关于 {@code java.util.LinkedList<T>}
	 *         实现的新实例。
	 */
	public static <T> List<T> getLinkedList() {
		return new LinkedList<T>();
	}

	/**
	 * 用该方法来代替 {@code new HashSet<T>()} 方式获得新的 {@code java.util.Set} 的实例对象。
	 * 
	 * @param <T>
	 *            {@code Set<T>} 中保存的对象。
	 * @return 返回 {@code java.util.Set<T>} 关于 {@code java.util.HashSet<T>}
	 *         实现的新实例。
	 */
	public static <T> Set<T> getHashSet() {
		return new HashSet<T>();
	}

	/**
	 * 用该方法来代替 {@code new HashSet<T>(int)} 方式获得新的 {@code java.util.Set} 的实例对象。
	 * 
	 * @param <T>
	 *            {@code Set<T>} 中保存的对象。
	 * @param initialCapacity
	 *            列表的初始容量。
	 * @return 返回 {@code java.util.Set<T>} 关于 {@code java.util.HashSet<T>}
	 *         实现的新实例。
	 */
	public static <T> Set<T> getHashSet(int initialCapacity) {
		return new HashSet<T>(initialCapacity);
	}

	/**
	 * 用该方法来代替 <code>new HashSet<T>(Collection<? extends T> c)</code> 方式获得新的
	 * {@code java.util.Set} 的实例对象。
	 * 
	 * @param <T>
	 *            {@code Set} 中保存的对象。
	 * @param c
	 *            其中的元素将存放在新的 {@code set} 中的 {@code collection}。
	 * @return 返回 {@code java.util.Set<T>} 关于 {@code java.util.HashSet<T>}
	 *         实现的新实例。
	 */
	public static <T> Set<T> getHashSet(Collection<? extends T> c) {
		if (isEmpty(c))
			return new HashSet<T>();
		return new HashSet<T>(c);
	}

	/**
	 * 用该方法来代替 {@code new TreeSet<T>()} 方式获得新的 {@code java.util.Set} 的实例对象。
	 * 
	 * @param <T>
	 *            {@code Set<T>} 中保存的对象。
	 * @return 返回 {@code java.util.Set<T>} 关于 {@code java.util.TreeSet<T>}
	 *         实现的新实例。
	 */
	public static <T> Set<T> getTreeSet() {
		return new TreeSet<T>();
	}

	/**
	 * 用该方法来代替 <code>new TreeSet<T>(Collection<? extends T> c)</code> 方式获得新的
	 * {@code java.util.Set} 的实例对象。
	 * 
	 * @param <T>
	 *            {@code Set} 中保存的对象。
	 * @param c
	 *            其中的元素将存放在新的 {@code set} 中的 {@code collection}。
	 * @return 返回 {@code java.util.Set<T>} 关于 {@code java.util.TreeSet<T>}
	 *         实现的新实例。
	 */
	public static <T> Set<T> getTreeSet(Collection<? extends T> c) {
		if (isEmpty(c))
			return new TreeSet<T>();
		return new TreeSet<T>(c);
	}

	/**
	 * 用该方法来代替 {@code new LinkedList<E>()} 方式获得新的 {@code java.util.Queue} 的实例对象。
	 * 
	 * @param <E>
	 *            {@code Queue<E>} 中保存的对象。
	 * @return 返回 {@code java.util.Queue<E>} 关于 {@code java.util.LinkedList<E>}
	 *         实现的新实例。
	 */
	public static <E> Queue<E> getQueue() {
		return new LinkedList<E>();
	}

	/**
	 * 合并两个有相同元素类型的 {@code java.util.Set}。
	 * <ul>
	 * <li>{@code setA == null && setB == null} --> 返回 {@link #getHashSet()}。</li>
	 * <li>{@code setA != null && setB == null} --> 返回 {@code setA}。</li>
	 * <li>{@code setA == null && setB != null} --> 返回 {@code setB}。</li>
	 * <li>{@code setA != null && setB != null} --> 返回 {@code setA} 和
	 * {@code setB} 的并集。</li>
	 * </ul>
	 * 
	 * @param <T>
	 *            {@code Set} 中保存的对象。
	 * @param setA
	 *            第一个 {@code Set}。
	 * @param setB
	 *            第二个 {@code Set}。
	 * @return 返回 {@code setA} 和 {@code setB} 的并集。
	 */
	public static <T> Set<T> unionHashSet(Set<T> setA, Set<T> setB) {
		boolean isEmptySetA = isEmpty(setA);
		boolean isEmptySetB = isEmpty(setB);
		if (isEmptySetA && isEmptySetB)
			return getHashSet();
		if (isEmptySetA && !isEmptySetB)
			return setB;
		if (!isEmptySetA && isEmptySetB)
			return setA;
		Set<T> result = getHashSet(setA);
		result.addAll(setB);
		return result;
	}

	/**
	 * 取两个有相同元素类型的 {@code java.util.Set} 的交集，即公共部份的新的 {@code java.util.Set}。
	 * <ul>
	 * <li>{@code setA == null && setB == null} --> 返回 {@code null}。</li>
	 * <li>{@code setA != null && setB == null} --> 返回 {@code null}。</li>
	 * <li>{@code setA == null && setB != null} --> 返回 {@code null}。</li>
	 * <li>{@code setA != null && setB != null} --> 返回 {@code setA} 和
	 * {@code setB} 的交集。</li>
	 * </ul>
	 * 
	 * @param <T>
	 *            {@code Set} 中保存的对象。
	 * @param setA
	 *            第一个 {@code Set}。
	 * @param setB
	 *            第二个 {@code Set}。
	 * @return 返回 {@code setA} 和 {@code setB} 的交集。
	 */
	public static <T> Set<T> intersectHashSet(Set<T> setA, Set<T> setB) {
		if (isEmpty(setA) || isEmpty(setB))
			return null;
		Set<T> result = getHashSet(setA);
		result.retainAll(setB);
		return result;
	}

	/**
	 * 移除 {@code setA} 中那些包含在 {@code setB} 中的元素。<br />
	 * 此方法不会修改 {@code setA}，只是复制一份作相应操作，返回的是全新的 {@code Set} 对象。
	 * <ul>
	 * <li>{@code setA == null} --> 返回 {@code null}。</li>
	 * <li>{@code setB == null} --> 返回 {@code setA}。</li>
	 * <li>{@code setA != null && setB != null} --> 返回 {@code setA} 和
	 * {@code setB} 的不对称差集。</li>
	 * </ul>
	 * 
	 * @param <T>
	 *            {@code Set} 中保存的对象。
	 * @param setA
	 *            第一个 {@code Set}。
	 * @param setB
	 *            第二个 {@code Set}。
	 * @return 返回 {@code setA} 和 {@code setB} 的不对称差集。
	 */
	public static <T> Set<T> differenceHashSet(Set<T> setA, Set<T> setB) {
		if (isEmpty(setA))
			return null;
		if (isEmpty(setB))
			return setA;
		Set<T> result = getHashSet(setA);
		result.removeAll(setB);
		return result;
	}

	/**
	 * 取两个有相同元素类型的 {@code java.util.Set} 的补集。
	 * 
	 * @param <T>
	 *            {@code Set} 中保存的对象。
	 * @param setA
	 *            第一个 {@code Set}。
	 * @param setB
	 *            第二个 {@code Set}。
	 * @return 返回 {@code setA} 和 {@code setB} 的补集。
	 */
	public static <T> Set<T> complementHashSet(Set<T> setA, Set<T> setB) {
		return differenceHashSet(unionHashSet(setA, setB),
				intersectHashSet(setA, setB));
	}
}
