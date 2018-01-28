   // dimensional arrays
   _______________________________
   
   array[]        // 1 dimentional
   
     0 
     _
  0 |_|
  1 |_|
  2 |_| height
  ________________________________
  
   array[][]      // 2 dimentional
   
     0 1 2
     _ _ _
  0 |_|_|_|
  1 |_|_|_|
  2 |_|_|_| width + height
  ________________________________
  
   array[][][]    // 3 dimentional
   
     0 1 2
      _ _ _
     /_/_/_/|
  0 |_|_|_|/|
  1 |_|_|_|/|
  2 |_|_|_|/  width + height + depth
  
  ________________________________
  
  // needs nested loops to run through them
  
for (loop through a row X)
{
   for (loop through each collumn in the row Y)
   {
      for (loop through each collumn back Z)
   }
}

________________________________________

   // FOR EACH LOOP

int[] primes = {2, 3, 5, 7, 11, 13}
for (int p : primes)
{
   System.out.println(p);
}

// for each p in primes, print p

output:
start

2
3
5
7
11
13

end

________________________________________


