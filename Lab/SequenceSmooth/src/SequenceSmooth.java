import components.sequence.Sequence;

/**
 * Implements method to smooth a {@code Sequence<Integer>}.
 * 
 * @author Put your name here
 * 
 */
public final class SequenceSmooth {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private SequenceSmooth() {
    }

    /**
     * Smooths a given {@code Sequence<Integer>}.
     * 
     * @param s1
     *            the sequence to smooth
     * @param s2
     *            the resulting sequence
     * @replaces s2
     * @requires |s1| >= 1
     * @ensures <pre>
     * |s2| = |s1| - 1  and 
     *  for all i, j: integer, a, b: string of integer
     *      where (s1 = a * <i> * <j> * b)
     *    (there exists c, d: string of integer
     *       (|c| = |a|  and
     *        s2 = c * <(i+j)/2> * d))
     * </pre>
     */
    public static void smooth(Sequence<Integer> s1, Sequence<Integer> s2) {
        assert s1 != null : "Violation of: s1 is not null";
        assert s2 != null : "Violation of: s2 is not null";
        assert s1 != s2 : "Violation of: s1 is not s2";
        assert s1.length() >= 1 : "Violation of: |s1| >= 1";

        //while loop
        /*
        int len1 = s1.length();
        s2.clear();
        int i = 0;
        while(i<len1-1) {
        	int before = s1.entry(i);
        	int after = s1.entry(i+1);
        	int avg = (int)((long)before+after)/2;
        	//better not to write long type
        	s2.add(i, avg);
        	i++;
        }
        */
        
        int len1 =s1.length();
        if(len1 <= 1) {
        	s2.clear();
        }
        else {
        	int before = s1.remove(0);
        	int after = s1.entry(0);
        	int avg = 0;
        	if(before%2==1&&after%2==1) {
        		avg = before/2+after/2+1;
        	}
        	else {
        		avg = before/2+after/2;
        	}
        	smooth(s1,s2);
        	s2.add(0,avg );
        	s1.add(0, before);
        	
        }
        

    }

}