//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wydexcel.generate.common;

import java.util.*;

class WeakFastHashMap<K, V> extends HashMap<K, V> {
    private Map<K, V> map = null;
    private boolean fast = false;

    public WeakFastHashMap() {
        this.map = this.createMap();
    }

    public WeakFastHashMap(int capacity) {
        this.map = this.createMap(capacity);
    }

    public WeakFastHashMap(int capacity, float factor) {
        this.map = this.createMap(capacity, factor);
    }

    public WeakFastHashMap(Map<? extends K, ? extends V> map) {
        this.map = this.createMap(map);
    }

    public boolean getFast() {
        return this.fast;
    }

    public void setFast(boolean fast) {
        this.fast = fast;
    }

    public V get(Object key) {
        if (this.fast) {
            return this.map.get(key);
        } else {
            synchronized(this.map) {
                return this.map.get(key);
            }
        }
    }

    public int size() {
        if (this.fast) {
            return this.map.size();
        } else {
            synchronized(this.map) {
                return this.map.size();
            }
        }
    }

    public boolean isEmpty() {
        if (this.fast) {
            return this.map.isEmpty();
        } else {
            synchronized(this.map) {
                return this.map.isEmpty();
            }
        }
    }

    public boolean containsKey(Object key) {
        if (this.fast) {
            return this.map.containsKey(key);
        } else {
            synchronized(this.map) {
                return this.map.containsKey(key);
            }
        }
    }

    public boolean containsValue(Object value) {
        if (this.fast) {
            return this.map.containsValue(value);
        } else {
            synchronized(this.map) {
                return this.map.containsValue(value);
            }
        }
    }

    public V put(K key, V value) {
        if (this.fast) {
            synchronized(this) {
                Map<K, V> temp = this.cloneMap(this.map);
                V result = temp.put(key, value);
                this.map = temp;
                return result;
            }
        } else {
            synchronized(this.map) {
                return this.map.put(key, value);
            }
        }
    }

    public void putAll(Map<? extends K, ? extends V> in) {
        if (this.fast) {
            synchronized(this) {
                Map<K, V> temp = this.cloneMap(this.map);
                temp.putAll(in);
                this.map = temp;
            }
        } else {
            synchronized(this.map) {
                this.map.putAll(in);
            }
        }

    }

    public V remove(Object key) {
        if (this.fast) {
            synchronized(this) {
                Map<K, V> temp = this.cloneMap(this.map);
                V result = temp.remove(key);
                this.map = temp;
                return result;
            }
        } else {
            synchronized(this.map) {
                return this.map.remove(key);
            }
        }
    }

    public void clear() {
        if (this.fast) {
            synchronized(this) {
                this.map = this.createMap();
            }
        } else {
            synchronized(this.map) {
                this.map.clear();
            }
        }

    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof Map)) {
            return false;
        } else {
            Map<?, ?> mo = (Map)o;
            Object key;
            if (this.fast) {
                if (mo.size() != this.map.size()) {
                    return false;
                } else {
                    Iterator var3 = this.map.entrySet().iterator();

//                    Object key;
                    label64:
                    do {
                        do {
                            if (!var3.hasNext()) {
                                return true;
                            }

                            Entry<K, V> e = (Entry)var3.next();
                            key = e.getKey();
                            key = e.getValue();
                            if (key == null) {
                                continue label64;
                            }
                        } while(key.equals(mo.get(key)));

                        return false;
                    } while(mo.get(key) == null && mo.containsKey(key));

                    return false;
                }
            } else {
                synchronized(this.map) {
                    if (mo.size() != this.map.size()) {
                        return false;
                    } else {
                        Iterator var4 = this.map.entrySet().iterator();

                        label77:
                        do {
                            Object value;
                            do {
                                if (!var4.hasNext()) {
                                    return true;
                                }

                                Entry<K, V> e = (Entry)var4.next();
                                key = e.getKey();
                                value = e.getValue();
                                if (value == null) {
                                    continue label77;
                                }
                            } while(value.equals(mo.get(key)));

                            return false;
                        } while(mo.get(key) == null && mo.containsKey(key));

                        return false;
                    }
                }
            }
        }
    }

    public int hashCode() {
        if (this.fast) {
            int h = 0;

            Entry e;
            for(Iterator var7 = this.map.entrySet().iterator(); var7.hasNext(); h += e.hashCode()) {
                e = (Entry)var7.next();
            }

            return h;
        } else {
            synchronized(this.map) {
                int h = 0;

                Entry e;
                for(Iterator var3 = this.map.entrySet().iterator(); var3.hasNext(); h += e.hashCode()) {
                    e = (Entry)var3.next();
                }

                return h;
            }
        }
    }

    public Object clone() {
        WeakFastHashMap<K, V> results = null;
        if (this.fast) {
            results = new WeakFastHashMap(this.map);
        } else {
            synchronized(this.map) {
                results = new WeakFastHashMap(this.map);
            }
        }

        results.setFast(this.getFast());
        return results;
    }

    public Set<Entry<K, V>> entrySet() {
        return new EntrySet();
    }

    public Set<K> keySet() {
        return new KeySet();
    }

    public Collection<V> values() {
        return new Values();
    }

    protected Map<K, V> createMap() {
        return new WeakHashMap();
    }

    protected Map<K, V> createMap(int capacity) {
        return new WeakHashMap(capacity);
    }

    protected Map<K, V> createMap(int capacity, float factor) {
        return new WeakHashMap(capacity, factor);
    }

    protected Map<K, V> createMap(Map<? extends K, ? extends V> map) {
        return new WeakHashMap(map);
    }

    protected Map<K, V> cloneMap(Map<? extends K, ? extends V> map) {
        return this.createMap(map);
    }

    private class EntrySet extends CollectionView<Entry<K, V>> implements Set<Entry<K, V>> {
        private EntrySet() {
            super();
        }

        protected Collection<Entry<K, V>> get(Map<K, V> map) {
            return map.entrySet();
        }

        protected Entry<K, V> iteratorNext(Entry<K, V> entry) {
            return entry;
        }
    }

    private class Values extends CollectionView<V> {
        private Values() {
            super();
        }

        protected Collection<V> get(Map<K, V> map) {
            return map.values();
        }

        protected V iteratorNext(Entry<K, V> entry) {
            return entry.getValue();
        }
    }

    private class KeySet extends CollectionView<K> implements Set<K> {
        private KeySet() {
            super();
        }

        protected Collection<K> get(Map<K, V> map) {
            return map.keySet();
        }

        protected K iteratorNext(Entry<K, V> entry) {
            return entry.getKey();
        }
    }

    private abstract class CollectionView<E> implements Collection<E> {
        public CollectionView() {
        }

        protected abstract Collection<E> get(Map<K, V> var1);

        protected abstract E iteratorNext(Entry<K, V> var1);

        public void clear() {
            if (WeakFastHashMap.this.fast) {
                synchronized(WeakFastHashMap.this) {
                    WeakFastHashMap.this.map = WeakFastHashMap.this.createMap();
                }
            } else {
                synchronized(WeakFastHashMap.this.map) {
                    this.get(WeakFastHashMap.this.map).clear();
                }
            }

        }

        public boolean remove(Object o) {
            if (WeakFastHashMap.this.fast) {
                synchronized(WeakFastHashMap.this) {
                    Map<K, V> temp = WeakFastHashMap.this.cloneMap(WeakFastHashMap.this.map);
                    boolean r = this.get(temp).remove(o);
                    WeakFastHashMap.this.map = temp;
                    return r;
                }
            } else {
                synchronized(WeakFastHashMap.this.map) {
                    return this.get(WeakFastHashMap.this.map).remove(o);
                }
            }
        }

        public boolean removeAll(Collection<?> o) {
            if (WeakFastHashMap.this.fast) {
                synchronized(WeakFastHashMap.this) {
                    Map<K, V> temp = WeakFastHashMap.this.cloneMap(WeakFastHashMap.this.map);
                    boolean r = this.get(temp).removeAll(o);
                    WeakFastHashMap.this.map = temp;
                    return r;
                }
            } else {
                synchronized(WeakFastHashMap.this.map) {
                    return this.get(WeakFastHashMap.this.map).removeAll(o);
                }
            }
        }

        public boolean retainAll(Collection<?> o) {
            if (WeakFastHashMap.this.fast) {
                synchronized(WeakFastHashMap.this) {
                    Map<K, V> temp = WeakFastHashMap.this.cloneMap(WeakFastHashMap.this.map);
                    boolean r = this.get(temp).retainAll(o);
                    WeakFastHashMap.this.map = temp;
                    return r;
                }
            } else {
                synchronized(WeakFastHashMap.this.map) {
                    return this.get(WeakFastHashMap.this.map).retainAll(o);
                }
            }
        }

        public int size() {
            if (WeakFastHashMap.this.fast) {
                return this.get(WeakFastHashMap.this.map).size();
            } else {
                synchronized(WeakFastHashMap.this.map) {
                    return this.get(WeakFastHashMap.this.map).size();
                }
            }
        }

        public boolean isEmpty() {
            if (WeakFastHashMap.this.fast) {
                return this.get(WeakFastHashMap.this.map).isEmpty();
            } else {
                synchronized(WeakFastHashMap.this.map) {
                    return this.get(WeakFastHashMap.this.map).isEmpty();
                }
            }
        }

        public boolean contains(Object o) {
            if (WeakFastHashMap.this.fast) {
                return this.get(WeakFastHashMap.this.map).contains(o);
            } else {
                synchronized(WeakFastHashMap.this.map) {
                    return this.get(WeakFastHashMap.this.map).contains(o);
                }
            }
        }

        public boolean containsAll(Collection<?> o) {
            if (WeakFastHashMap.this.fast) {
                return this.get(WeakFastHashMap.this.map).containsAll(o);
            } else {
                synchronized(WeakFastHashMap.this.map) {
                    return this.get(WeakFastHashMap.this.map).containsAll(o);
                }
            }
        }

        public <T> T[] toArray(T[] o) {
            if (WeakFastHashMap.this.fast) {
                return this.get(WeakFastHashMap.this.map).toArray(o);
            } else {
                synchronized(WeakFastHashMap.this.map) {
                    return this.get(WeakFastHashMap.this.map).toArray(o);
                }
            }
        }

        public Object[] toArray() {
            if (WeakFastHashMap.this.fast) {
                return this.get(WeakFastHashMap.this.map).toArray();
            } else {
                synchronized(WeakFastHashMap.this.map) {
                    return this.get(WeakFastHashMap.this.map).toArray();
                }
            }
        }

        public boolean equals(Object o) {
            if (o == this) {
                return true;
            } else if (WeakFastHashMap.this.fast) {
                return this.get(WeakFastHashMap.this.map).equals(o);
            } else {
                synchronized(WeakFastHashMap.this.map) {
                    return this.get(WeakFastHashMap.this.map).equals(o);
                }
            }
        }

        public int hashCode() {
            if (WeakFastHashMap.this.fast) {
                return this.get(WeakFastHashMap.this.map).hashCode();
            } else {
                synchronized(WeakFastHashMap.this.map) {
                    return this.get(WeakFastHashMap.this.map).hashCode();
                }
            }
        }

        public boolean add(E o) {
            throw new UnsupportedOperationException();
        }

        public boolean addAll(Collection<? extends E> c) {
            throw new UnsupportedOperationException();
        }

        public Iterator<E> iterator() {
            return new CollectionViewIterator();
        }

        private class CollectionViewIterator implements Iterator<E> {
            private Map<K, V> expected;
            private Entry<K, V> lastReturned = null;
            private final Iterator<Entry<K, V>> iterator;

            public CollectionViewIterator() {
                this.expected = WeakFastHashMap.this.map;
                this.iterator = this.expected.entrySet().iterator();
            }

            public boolean hasNext() {
                if (this.expected != WeakFastHashMap.this.map) {
                    throw new ConcurrentModificationException();
                } else {
                    return this.iterator.hasNext();
                }
            }

            public E next() {
                if (this.expected != WeakFastHashMap.this.map) {
                    throw new ConcurrentModificationException();
                } else {
                    this.lastReturned = (Entry)this.iterator.next();
                    return CollectionView.this.iteratorNext(this.lastReturned);
                }
            }

            public void remove() {
                if (this.lastReturned == null) {
                    throw new IllegalStateException();
                } else {
                    if (WeakFastHashMap.this.fast) {
                        synchronized(WeakFastHashMap.this) {
                            if (this.expected != WeakFastHashMap.this.map) {
                                throw new ConcurrentModificationException();
                            }

                            WeakFastHashMap.this.remove(this.lastReturned.getKey());
                            this.lastReturned = null;
                            this.expected = WeakFastHashMap.this.map;
                        }
                    } else {
                        this.iterator.remove();
                        this.lastReturned = null;
                    }

                }
            }
        }
    }
}
