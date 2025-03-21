//Ex. 1.2.6

import java.io.*;
import java.util.*;
import java.text.*;

class Ssq1Sum {                                 /* sum of ...           */
  double delay;                                 /*   delay times        */
  double wait;                                  /*   wait times         */
  double service;                               /*   service times      */
  double interarrival;                          /*   interarrival times */

  void initSumParas() {
    delay = 0.0;
    wait = 0.0;
    service = 0.0;
    interarrival = 0.0;
  }
}

class ex_2 {
  static String FILENAME = "ac.dat";          /* input data file */
  static double START = 0.0;

  public static void main(String[] args) throws IOException {
      
    FileInputStream fis = null;
    try {
      fis = new FileInputStream(FILENAME);
    } catch (FileNotFoundException fnfe) {
      System.out.println("Cannot open input file" + FILENAME);
      System.exit(1);
    }
      
    InputStreamReader r = new InputStreamReader(fis);
    BufferedReader in = new BufferedReader(r);
    try {
      String line = null;
      StringTokenizer st = null;
      long   index     = 0;                     /* job index            */
      double arrival   = START;                 /* arrival time         */
      double delay;                             /* delay in queue       */
      double service;                           /* service time         */
      double wait;                              /* delay + service      */
      double departure = START;                 /* departure time       */
      Ssq1Sum sum = new Ssq1Sum();
      sum.initSumParas();

      while ( (line = in.readLine()) != null ) {
        index++;
        st = new StringTokenizer(line);
        arrival = Double.parseDouble(st.nextToken());
        if (arrival < departure)
          delay    = departure - arrival;       /* delay in queue    */
        else
          delay    = 0.0;                       /* no delay          */

        departure = Double.parseDouble(st.nextToken());
        service= departure - arrival - delay;
        wait         = delay + service;
        sum.delay   += delay;
        sum.wait    += wait;
        sum.service += service;
      }
      sum.interarrival = arrival - START;

      DecimalFormat f = new DecimalFormat("###0.00");

      double avg_service_time = sum.service / index;
      double avg_interarrival_time = sum.interarrival / index;
      double utilization = sum.service / departure;
      double traffic_intensity = avg_service_time / avg_interarrival_time;

      System.out.println("\nfor " + index + " jobs");
      System.out.println("   average interarrival time =  " + f.format(sum.interarrival / index));
      System.out.println("   average service time .... =  " + f.format(sum.service / index));
      System.out.println("   average delay ........... =  " + f.format(sum.delay / index));
      System.out.println("   average wait ............ =  " + f.format(sum.wait / index));
      System.out.println("   server utilization   = " + f.format(utilization));
      System.out.println("   traffic intensity    = " + f.format(traffic_intensity));

    } catch (EOFException eofe) {
      System.out.println("Ssq1:" + eofe);
    }
    fis.close();
  }
    
}
