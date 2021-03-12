# Challenge 2 - We want a Shrubbery!!

Are you able to help us find the Holly Grail of the python world?
Use as many tricks, libraries and crazy ideas you want. Be yourself and find us that shruberry.

## Excercises
1. Write a script that has the following output:
```
* 
* * 
* * * 
* * * * 
* * * * * 
* * * * 
* * * 
* * 
*
```

2. Remove the escaped quote ( \ ' ) and newline (\n) form the following dictionary:
 
```
{'cus_flow_headers': '\'column.format:"Protocol","%p","Source","%rs","udp_stream_2", "%Cus:udp.stream:2"\'\n', 'table_flow_headers': '\'-e frame.number -e _ws.col.Protocol -E aggregator="$" -E separator=/t -E header=y\'\n', 'table_features_headers': '\'-e frame.number  -E separator=/t\''}
```

3. Sort the following list of dictionaries by color:
 
```
mobiles = [{'make':'iPhone', 'model': 7, 'color':'White'}, {'make':'Google', 'model':'2', 'color':'Gold'}, {'make':'Nokia', 'model':216, 'color':'Black'}, {'make':'Samsung', 'model': 7, 'color':'Blue'}]
```

4. Square only the even numbers in the below list, but if they are odd numbers cube them. Store the results in a list

```
[1,2,4,5,6,9,13,15,10,4]
```

5. Create a threading script with the following rules

    a. Create a module containing the main threading running logic of the workers
    
    b. Call that module into a script where you will run your threads

## Bonus round:
Get some extra points if you have extra time (chose either or both)

**__NOTE: Currently the second bonus is not available. But feel free to add something similar that you think shows some value when working with files__**

1. Create a simple vending machine threading example with the following rules:

     a. Set a MAX number of candies
     
     b. Buy candies until the vending machine is empty
     
     c. Refill the machine at least 3 times while you buy candy
     
     d. Exit once you have emptied the machine.
   
     (**__HINT:__** Traffic lights and locks )
     
2. On the [bonus2_files](./bonus2_files) folder you have 2 files. A binary and an excel file. **__(NOT AVAILABLE YET)__**

     a. Use the excel file to get start and end timestamps
     
     b. Split the binary file. You should have a new binary file per 
     
     (**__HINT:__** You need to use [editcap](https://www.wireshark.org/docs/man-pages/editcap.html) to split the files.
      
## Expected steps
+ Create a branch of this project (or fork it in your github account if you prefer)
+ Do you **_thang_** inside this folder (challenge-1)
+ Push your change inside a Pull Request to our master
