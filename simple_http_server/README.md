# My Simple Http Server

1. Compile and run `MyHttpServer`
2. Now open your browser and go to `localhost:8000/test`
3. You should receive 
```
This is the response
```

# Exercise

1. Implement a joke endpoint returning random joke from prepared list of jokes (it can be stored as static list of Strings)
For example:
`GET /joke` should return:

```
Q: How long do chickens work?
A: Around the cluck!
```
hope you come with better than this one :-)

2. Implement a hello endpoint receiving your name as parameter and returning personal greeting message 
`GET /hello?name=Janusz`
```
Janusz you look great today!
```

3. Implement is prime number endpoint checking whenever given number is prime or not.
`GET /isPrime?number=7`
```
7 is prime
```

`GET /isPrime?number=8`
```
8 is not a prime
```

4. Implement fibonacci endpoint, the same way as above