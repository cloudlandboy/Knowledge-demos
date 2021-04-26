package cn.clboy.demo.redis.springboot.single.utils;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;


public class RedisUtil<K, V> {


    private RedisTemplate<K, V> redisTemplate;

    public RedisUtil(RedisTemplate<K, V> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<K, V> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public RedisTemplate<K, V> getRedisTemplate() {
        return this.redisTemplate;
    }


    /**
     *
     * @param key
     * @param value
     */
    public void set(K key, V value) {
        redisTemplate.opsForValue().set(key, value);
    }


    /**
     *
     * @param key
     * @return
     */
    public V get(K key) {
        return (V) redisTemplate.opsForValue().get(key);
    }

    /**
     * Set the String type key-value and add the expiration time (in milliseconds)
     *
     * @param key
     * @param value
     * @param time  expiration time, millisecond unit
     */
    public void setForTimeMS(K key, V value, long time) {
        redisTemplate.opsForValue().set(key, value, time, TimeUnit.MILLISECONDS);
    }

    /**
     * Set the String type key-value and add the expiration time (in minutes)
     *
     * @param key
     * @param value
     * @param time  expiration time, minute unit
     */
    public void setForTimeMIN(K key, V value, long time) {
        redisTemplate.opsForValue().set(key, value, time, TimeUnit.MINUTES);
    }


    /**
     * Set the String type key-value and add the expiration time (in minutes)
     *
     * @param key
     * @param value
     * @param time  expiration time, minute unit
     */
    public void setForTimeCustom(K key, V value, long time, TimeUnit type) {
        redisTemplate.opsForValue().set(key, value, time, type);
    }

    /**
     * Overwrites if key exists and returns the old value.
     * If it does not exist, return null and add
     *
     * @param key
     * @param value
     * @return
     */
    public V getAndSet(K key, V value) {
        return (V) redisTemplate.opsForValue().getAndSet(key, value);
    }


    /**
     * Add key-value in batches (repeated keys will be overwritten)
     *
     * @param keyAndValue
     */
    public void batchSet(Map<K, V> keyAndValue) {
        redisTemplate.opsForValue().multiSet(keyAndValue);
    }

    /**
     * Add key-value in batches only if the key does not exist
     * As long as there is a key in the map, all are not added.
     *
     * @param keyAndValue
     */
    public void batchSetIfAbsent(Map<K, V> keyAndValue) {
        redisTemplate.opsForValue().multiSetIfAbsent(keyAndValue);
    }

    /**
     * Add and subtract a value of a key-value,
     * If the key does not exist, a key will be created and the number will be assigned
     * If key exists, but value is not long, an error will be reported
     *
     * @param key
     * @param number
     */
    public Long increment(K key, long number) {
        return redisTemplate.opsForValue().increment(key, number);
    }

    /**
     * Add and subtract a value of a key-value,
     * If the key does not exist, a key will be created and the number will be assigned
     * If key exists, but value is not a pure number, an error will be reported
     *
     * @param key
     * @param number
     */
    public Double increment(K key, double number) {
        return redisTemplate.opsForValue().increment(key, number);
    }


    /**
     * Append expiration time to a specified key value
     *
     * @param key
     * @param time
     * @param type
     * @return
     */
    public boolean expire(K key, long time, TimeUnit type) {
        return redisTemplate.boundValueOps(key).expire(time, type);
    }

    /**
     * Remove the expiration time of the specified key
     *
     * @param key
     * @return
     */
    public boolean persist(K key) {
        return redisTemplate.boundValueOps(key).persist();
    }


    /**
     * Get the expiration time of the specified key
     *
     * @param key
     * @return
     */
    public Long getExpire(K key) {
        return redisTemplate.boundValueOps(key).getExpire();
    }

    /**
     * Modify key
     *
     * @param key
     * @return
     */
    public void rename(K key, K newKey) {
        redisTemplate.boundValueOps(key).rename(newKey);
    }

    /**
     * delete key-value
     *
     * @param key
     * @return
     */
    public void delete(K key) {
        redisTemplate.delete(key);
    }

    //hash operation

    /**
     * Add Hash key-value pairs
     *
     * @param key
     * @param hashKey
     * @param value
     */
    public void put(K key, Object hashKey, V value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    /**
     * Add hash key-value pairs in batches
     * There is coverage, if not, add
     *
     * @param key
     * @param map
     */
    public void putAll(K key, Map<K, V> map) {
        redisTemplate.opsForHash().putAll(key, map);
    }

    /**
     * Add a hash key-value pair. Add it when it doesn't exist.
     *
     * @param key
     * @param hashKey
     * @param value
     * @return
     */
    public boolean putIfAbsent(K key, Object hashKey, V value) {
        return redisTemplate.opsForHash().putIfAbsent(key, hashKey, value);
    }


    /**
     * Delete the HashKey of the specified hash
     *
     * @param key
     * @param hashKeys
     * @return deletes the number of successes
     */
    public Long delete(K key, Object... hashKeys) {
        return redisTemplate.opsForHash().delete(key, hashKeys);
    }


    /**
     * Add or subtract the hashkey of the specified hash
     *
     * @param key
     * @param hashKey
     * @param number
     * @return
     */
    public Long increment(K key, Object hashKey, long number) {
        return redisTemplate.opsForHash().increment(key, hashKey, number);
    }

    /**
     * Add or subtract the hashkey of the specified hash
     *
     * @param key
     * @param hashKey
     * @param number
     * @return
     */
    public Double increment(K key, Object hashKey, Double number) {
        return redisTemplate.opsForHash().increment(key, hashKey, number);
    }

    /**
     * Get the hashkey under the specified key
     *
     * @param key
     * @param hashKey
     * @return
     */
    public Object getHashKey(K key, Object hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }


    /**
     * Get all hashkeys and values ​​under key
     *
     * @param key
     * @return
     */
    public Map<Object, Object> getHashEntries(K key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * Verify that there is no hashkey specified under the specified key
     *
     * @param key
     * @param hashKey
     * @return
     */
    public boolean hashKey(K key, Object hashKey) {
        return redisTemplate.opsForHash().hasKey(key, hashKey);
    }

    /**
     * Get all hashkey field names under key
     *
     * @param key
     * @return
     */
    public Set<Object> hashKeys(K key) {
        return redisTemplate.opsForHash().keys(key);
    }


    /**
     * Get the number of key-value pairs under the specified hash
     *
     * @param key
     * @return
     */
    public Long hashSize(K key) {
        return redisTemplate.opsForHash().size(key);
    }

    //List operation

    /**
     * Specify list from left to stack
     *
     * @param key
     * @return the length of the current queue
     */
    public Long leftPush(K key, V value) {
        return redisTemplate.opsForList().leftPush(key, value);
    }

    /**
     * Specify list to pop out from the left
     * If the list has no elements, it will block the list until there is an element or timeout
     *
     * @param key
     * @return popped value
     */
    public Object leftPop(K key) {
        return redisTemplate.opsForList().leftPop(key);
    }

    /**
     * Push the stack from the left
     * Import order in the order of Collection
     * eg: a b c => c b a
     *
     * @param key
     * @param values
     * @return
     */
    public Long leftPushAll(K key, Collection<V> values) {
        return redisTemplate.opsForList().leftPushAll(key, values);
    }

    /**
     * Specify list from right to stack
     *
     * @param key
     * @return the length of the current queue
     */
    public Long rightPush(K key, V value) {
        return redisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * Specify list to pop out from the right
     * If the list has no elements, it will block the list until there is an element or timeout
     *
     * @param key
     * @return popped value
     */
    public V rightPop(K key) {
        return redisTemplate.opsForList().rightPop(key);
    }

    /**
     * Push the stack from the right
     * Import order in the order of Collection
     * eg: a b c => a b c
     *
     * @param key
     * @param values
     * @return
     */
    public Long rightPushAll(K key, Collection<V> values) {
        return redisTemplate.opsForList().rightPushAll(key, values);
    }


    /**
     * Get value based on subscript
     *
     * @param key
     * @param index
     * @return
     */
    public V index(K key, long index) {
        return redisTemplate.opsForList().index(key, index);
    }


    /**
     * Get the specified length of the list
     *
     * @param key
     * @return
     */
    public Long listSize(K key) {
        return redisTemplate.opsForList().size(key);
    }


    /**
     * Get list All values ​​in the specified range
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public List<V> listRange(K key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }


    /**
     * Delete the number of counts whose value is value in the key.
     *
     * @param key
     * @param count
     * @param value
     * @return The number of successfully deleted
     */
    public Long listRemove(K key, long count, V value) {
        return redisTemplate.opsForList().remove(key, count, value);
    }


    /**
     * Delete all elements except the list [start,end]
     *
     * @param key
     * @param start
     * @param end
     */
    public void listTrim(K key, long start, long end) {
        redisTemplate.opsForList().trim(key, start, end);

    }

    /**
     * Put the key right out of the stack and left onto the stack to key2
     *
     * @param key  List of right stacks
     * @param key2 left-to-stack list
     *             The value of the @return operation
     */
    public Object rightPopAndLeftPush(K key, K key2) {
        return redisTemplate.opsForList().rightPopAndLeftPush(key, key2);

    }

    //set operation unordered, non-repeating collection

    /**
     * Add set element
     *
     * @param key
     * @param values
     * @return
     */
    public Long add(K key, V... values) {
        return redisTemplate.opsForSet().add(key, values);
    }

    /**
     * Get the difference between two sets
     *
     * @param key
     * @param otherkey
     * @return
     */
    public Set<V> difference(K key, K otherkey) {
        return redisTemplate.opsForSet().difference(key, otherkey);
    }


    /**
     * Get the difference between key and set of key collections in collections
     *
     * @param key
     * @param otherKeys
     * @return
     */
    public Set<V> difference(K key, Collection<K> otherKeys) {
        return redisTemplate.opsForSet().difference(key, otherKeys);
    }

    /**
     * Add the difference between key and otherkey to the new newKey collection
     *
     * @param key
     * @param otherkey
     * @param newKey
     * @return returns the number of difference sets
     */
    public Long differenceAndStore(K key, K otherkey, K newKey) {
        return redisTemplate.opsForSet().differenceAndStore(key, otherkey, newKey);
    }

    /**
     * Add the difference set of the key collection in key and collection collections to the newkey collection
     *
     * @param key
     * @param otherKeys
     * @param newKey
     * @return returns the number of difference sets
     */
    public Long differenceAndStore(K key, Collection<K> otherKeys, K newKey) {
        return redisTemplate.opsForSet().differenceAndStore(newKey, otherKeys, newKey);
    }

    /**
     * Delete specified values ​​in one or more collections
     *
     * @param key
     * @param values
     * @return successfully deleted the quantity
     */
    public Long remove(K key, Object... values) {
        return redisTemplate.opsForSet().remove(key, values);
    }

    /**
     * Randomly remove an element and return it
     *
     * @param key
     * @return
     */
    public Object randomSetPop(K key) {
        return redisTemplate.opsForSet().pop(key);
    }

    /**
     * Get an element randomly
     *
     * @param key
     * @return
     */
    public Object randomSet(K key) {
        return redisTemplate.opsForSet().randomMember(key);
    }

    /**
     * Randomly get the specified number of elements, the same element may be selected twice
     *
     * @param key
     * @param count
     * @return
     */
    public List<V> randomSet(K key, long count) {
        return redisTemplate.opsForSet().randomMembers(key, count);
    }

    /**
     * Randomly get the specified number of elements, deduplicate (the same element can only be selected twice)
     *
     * @param key
     * @param count
     * @return
     */
    public Set<V> randomSetDistinct(K key, long count) {
        return redisTemplate.opsForSet().distinctRandomMembers(key, count);
    }

    /**
     * Transfer the value in key to destKey
     *
     * @param key
     * @param value
     * @param destKey
     * @return returns success or not
     */
    public boolean moveSet(K key, V value, K destKey) {
        return redisTemplate.opsForSet().move(key, value, destKey);
    }

    /**
     * The size of the unordered collection
     *
     * @param key
     * @return
     */
    public Long setSize(K key) {
        return redisTemplate.opsForSet().size(key);
    }

    /**
     * Determine if there is a value in the set collection
     *
     * @param key
     * @param value
     * @return
     */
    public boolean isMember(K key, V value) {
        return redisTemplate.opsForSet().isMember(key, value);
    }

    /**
     * Returns the union of key and othere
     *
     * @param key
     * @param otherKey
     * @return
     */
    public Set<V> unionSet(K key, K otherKey) {
        return redisTemplate.opsForSet().union(key, otherKey);
    }

    /**
     * Returns the union of key and otherKeys
     *
     * @param key
     * @param collection of otherKeys key
     * @return
     */
    public Set<V> unionSet(K key, Collection<K> otherKeys) {
        return redisTemplate.opsForSet().union(key, otherKeys);
    }

    /**
     * Save the union of key and otherKey to destKey
     *
     * @param key
     * @param otherKey
     * @param destKey
     * @return destKey Quantity
     */
    public Long unionAndStoreSet(K key, K otherKey, K destKey) {
        return redisTemplate.opsForSet().unionAndStore(key, otherKey, destKey);
    }

    /**
     * Save the union of key and otherKey to destKey
     *
     * @param key
     * @param otherKeys
     * @param destKey
     * @return destKey Quantity
     */
    public Long unionAndStoreSet(K key, Collection<K> otherKeys, K destKey) {
        return redisTemplate.opsForSet().unionAndStore(key, otherKeys, destKey);
    }

    /**
     * returns all elements in the collection
     *
     * @param key
     * @return
     */
    public Set<V> members(K key) {
        return redisTemplate.opsForSet().members(key);
    }

    //Zset sorts according to socre. Do not repeat each element with a socre double type attribute (double can be repeated)

    /**
     * Add ZSet element
     *
     * @param key
     * @param value
     * @param score
     */
    public boolean add(K key, V value, double score) {
        return redisTemplate.opsForZSet().add(key, value, score);
    }

    /**
     * Add Zsets in bulk <br>
     * Set<TypedTuple<V>> tuples = new HashSet<>();<br>
     * TypedTuple<V> objectTypedTuple1 = new DefaultTypedTuple<V>("zset-5",9.6);<br>
     * tuples.add(objectTypedTuple1);
     *
     * @param key
     * @param tuples
     * @return
     */
    public Long batchAddZset(K key, Set<TypedTuple<V>> tuples) {
        return redisTemplate.opsForZSet().add(key, tuples);
    }

    /**
     * Zset deletes one or more elements
     *
     * @param key
     * @param values
     * @return
     */
    public Long removeZset(K key, V... values) {
        return redisTemplate.opsForZSet().remove(key, values);
    }

    /**
     * Increase or decrease the value of the specified zset and the socre attribute
     *
     * @param key
     * @param value
     * @param score
     * @return
     */
    public Double incrementScore(K key, V value, double score) {
        return redisTemplate.opsForZSet().incrementScore(key, value, score);
    }

    /**
     * Get the rank of the specified value in key (starting at 0, sorted from small to large)
     *
     * @param key
     * @param value
     * @return
     */
    public Long rank(K key, V value) {
        return redisTemplate.opsForZSet().rank(key, value);
    }

    /**
     * Get the rank of the specified value in key (starting at 0, sorted from big to small)
     *
     * @param key
     * @param value
     * @return
     */
    public Long reverseRank(K key, V value) {
        return redisTemplate.opsForZSet().reverseRank(key, value);
    }

    /**
     * Get the sort result set in the index range (starting at 0, from small to large, with a score)
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<TypedTuple<V>> rangeWithScores(K key, long start, long end) {
        return redisTemplate.opsForZSet().rangeWithScores(key, start, end);
    }

    /**
     * Get the sort result set in the index range (starting at 0, from small to large, only column names)
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<V> range(K key, long start, long end) {
        return redisTemplate.opsForZSet().range(key, start, end);
    }

    /**
     * Get the sort result collection of [min,max] in the range of scores (from small to large, only column names)
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    public Set<V> rangeByScore(K key, double min, double max) {
        return redisTemplate.opsForZSet().rangeByScore(key, min, max);
    }

    /**
     * Get the sort result set of [min,max] in the range of scores (from small to large, with scores)
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    public Set<TypedTuple<V>> rangeByScoreWithScores(K key, double min, double max) {
        return redisTemplate.opsForZSet().rangeByScoreWithScores(key, min, max);
    }

    /**
     * Returns a collection of elements specifying the number of counts within the range of scores, starting with the offset subscript (from small to large, without a fractional collection)
     *
     * @param key
     * @param min
     * @param max
     * @param offset starts with the specified subscript
     * @param count  prints the specified number of elements
     * @return
     */
    public Set<V> rangeByScore(K key, double min, double max, long offset, long count) {
        return redisTemplate.opsForZSet().rangeByScore(key, min, max, offset, count);
    }

    /**
     * Returns a collection of elements specifying the number of counts within the range of scores, starting with the offset subscript (small to large, a collection with fractions)
     *
     * @param key
     * @param min
     * @param max
     * @param offset starts with the specified subscript
     * @param count  prints the specified number of elements
     * @return
     */
    public Set<TypedTuple<V>> rangeByScoreWithScores(K key, double min, double max, long offset, long count) {
        return redisTemplate.opsForZSet().rangeByScoreWithScores(key, min, max, offset, count);
    }

    /**
     * Get the sort result set in the index range (starting at 0, from big to small, only column names)
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<V> reverseRange(K key, long start, long end) {
        return redisTemplate.opsForZSet().reverseRange(key, start, end);
    }

    /**
     * Get the sort result set in the index range (starting at 0, from big to small, with a score)
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<TypedTuple<V>> reverseRangeWithScores(K key, long start, long end) {
        return redisTemplate.opsForZSet().reverseRangeWithScores(key, start, end);
    }

    /**
     * Get the sort result set of [min,max] in the range of scores (from big to small, no scores in the collection)
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    public Set<V> reverseRangeByScore(K key, double min, double max) {
        return redisTemplate.opsForZSet().reverseRangeByScore(key, min, max);
    }

    /**
     * Get the sort result set of [min,max] in the range of scores (from big to small, collection with score)
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    public Set<TypedTuple<V>> reverseRangeByScoreWithScores(K key, double min, double max) {
        return redisTemplate.opsForZSet().reverseRangeByScoreWithScores(key, min, max);
    }

    /**
     * Returns a collection of elements specifying the number of counts within the range of scores, starting with the offset subscript (from large to small, without a fractional collection)
     *
     * @param key
     * @param min
     * @param max
     * @param offset starts with the specified subscript
     * @param count  prints the specified number of elements
     * @return
     */
    public Set<V> reverseRangeByScore(K key, double min, double max, long offset, long count) {
        return redisTemplate.opsForZSet().reverseRangeByScore(key, min, max, offset, count);
    }

    /**
     * Returns a collection of elements specifying the number of counts within the range of scores, starting with the offset subscript (large to small, a set with fractions)
     *
     * @param key
     * @param min
     * @param max
     * @param offset starts with the specified subscript
     * @param count  prints the specified number of elements
     * @return
     */
    public Set<TypedTuple<V>> reverseRangeByScoreWithScores(K key, double min, double max, long offset, long count) {
        return redisTemplate.opsForZSet().reverseRangeByScoreWithScores(key, min, max, offset, count);
    }

    /**
     * Returns the number of elements in the specified fraction interval [min,max]
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    public long countZSet(K key, double min, double max) {
        return redisTemplate.opsForZSet().count(key, min, max);
    }

    /**
     * Returns the number of zset collections
     *
     * @param key
     * @return
     */
    public long sizeZset(K key) {
        return redisTemplate.opsForZSet().size(key);
    }

    /**
     * Get the score value of the specified member
     *
     * @param key
     * @param value
     * @return
     */
    public Double score(K key, V value) {
        return redisTemplate.opsForZSet().score(key, value);
    }

    /**
     * Delete members of the specified index position, where member scores are pressed (from small to large)
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Long removeRange(K key, long start, long end) {
        return redisTemplate.opsForZSet().removeRange(key, start, end);
    }

    /**
     * Delete members [main,max] within the specified score range, where member scores are pressed (from small to large)
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    public Long removeRangeByScore(K key, double min, double max) {
        return redisTemplate.opsForZSet().removeRangeByScore(key, min, max);
    }

    /**
     * The union of the two sets of key and other, stored in the destKey collection, the same score of the column name plus
     *
     * @param key
     * @param otherKey
     * @param destKey
     * @return
     */
    public Long unionAndStoreZset(K key, K otherKey, K destKey) {
        return redisTemplate.opsForZSet().unionAndStore(key, otherKey, destKey);
    }

    /**
     * The union of multiple collections of key and otherKeys is stored in the destKey collection, and the scores with the same column name are added.
     *
     * @param key
     * @param otherKeys
     * @param destKey
     * @return
     */
    public Long unionAndStoreZset(K key, Collection<K> otherKeys, K destKey) {
        return redisTemplate.opsForZSet().unionAndStore(key, otherKeys, destKey);
    }

    /**
     * The intersection of the two sets of key and otherKey, stored in the destKey collection
     *
     * @param key
     * @param otherKey
     * @param destKey
     * @return
     */
    public Long intersectAndStore(K key, K otherKey, K destKey) {
        return redisTemplate.opsForZSet().intersectAndStore(key, otherKey, destKey);
    }

    /**
     * The intersection of multiple sets of key and otherKeys, stored in the destKey collection
     *
     * @param key
     * @param otherKeys
     * @param destKey
     * @return
     */
    public Long intersectAndStore(K key, Collection<K> otherKeys, K destKey) {
        return redisTemplate.opsForZSet().intersectAndStore(key, otherKeys, destKey);
    }

}