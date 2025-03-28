import java.io.*;
import java.util.*;


class Rng {

  long MODULUS      = 2147483647; /* DON'T CHANGE THIS VALUE                   */
  long MULTIPLIER   = 48271;      /* use 16807 for the "minimal standard"      */
  static long CHECK = 399268537L; /* use 1043616065 for the "minimal standard" */
  long DEFAULT      = 123456789L; /* initial seed, use 0 < DEFAULT < MODULUS   */

  long seed         = DEFAULT;    /* seed is the state of the generator        */

  public static void main(String[] args) {

    Rng r = new Rng();
    r.testRandom();
  }

  
  public double random() {
/* ---------------------------------------------------------------------
 * Random is a Lehmer generator that returns a pseudo-random real number
 * uniformly distributed between 0.0 and 1.0.  The period is (m - 1)
 * where m = 2,147,483,647 amd the smallest and largest possible values
 * are (1 / m) and 1 - (1 / m) respectively.
 * ---------------------------------------------------------------------
 */

    long Q = MODULUS / MULTIPLIER;
    long R = MODULUS % MULTIPLIER;
    long t;

    t = MULTIPLIER * (seed % Q) - R * (seed / Q);
    if (t > 0)
      seed = t;
    else
      seed = t + MODULUS;
    return ((double) seed / MODULUS);
  }

  public void putSeed(long x) {
/* -------------------------------------------------------------------
 * Use this (optional) procedure to initialize or reset the state of
 * the random number generator according to the following conventions:
 *    if x > 0 then x is the initial seed (unless too large)
 *    if x < 0 then the initial seed is obtained from the system clock
 *    if x = 0 then the initial seed is to be supplied interactively
 * --------------------------------------------------------------------
 */
    boolean ok = false;

    if (x > 0L)
      x = x % MODULUS;                          /* correct if x is too large  */
    if (x < 0L) {
      Date now = new Date();
      x = now.getTime();
//      x = ((unsigned long) time((time_t *) NULL)) % MODULUS;
    }
    if (x == 0L)
      while (!ok) {
        try {
          System.out.print("\nEnter a positive integer seed (9 digits or less) >> ");
          String line;
          InputStreamReader r = new InputStreamReader(System.in);
          BufferedReader ReadThis = new BufferedReader(r);

          line = ReadThis.readLine();
          x = Long.parseLong(line);
        } catch (IOException e) {
        } catch (NumberFormatException nfe) {
        }
        ok = (0 < x) && (x < MODULUS);
        if (!ok)
          System.out.println("\nInput out of range ... try again\n");
      }
      
    seed = x;
  }

  public long getSeed() {
/* --------------------------------------------------------------------
 * Use this (optional) procedure to get the current state of the random
 * number generator.
 * --------------------------------------------------------------------
 */
    return seed;
  }

/* -------------------------------------------------------------------
 * Use this (optional) procedure to test for a correct implementation.
 * -------------------------------------------------------------------
 */
  public void testRandom() {
    long   i;
    long   x;
    double u;

    putSeed(1);                                 /* set initial state to 1 */
    for(i = 0; i < 10000; i++)
      u = random();
    x = getSeed();                              /* get the new state      */
    if (x == CHECK)
      System.out.println("\n The implementation of Random is correct");
    else
      System.out.println("\n ERROR - the implementation of Random is not correct");
  }
    
}
