    String s1 = new String("hello");
    String s2 = new String("hello");

    System.out.println(s1 == s2); // false (different objects in memory)
    System.out.println(s1.equals(s2)); // true (same string content)
