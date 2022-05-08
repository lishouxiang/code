package com.redis;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

public class JedisClusterDemo {

    public static void main(String[] args) {

        Set<HostAndPort> nodes = new HashSet<HostAndPort>();
        nodes.add(new HostAndPort("192.168.23.128",6379));
        JedisCluster jedisCluster = new JedisCluster(nodes);

        System.out.println(jedisCluster.get("dd"));

        jedisCluster.set("ceshi","123456789");

    }

}
