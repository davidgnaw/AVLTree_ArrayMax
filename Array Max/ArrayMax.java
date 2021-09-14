// THIS CODE IS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING
// A TUTOR OR CODE WRITTEN BY OTHER STUDENTS - Yu Fung David Wang


import java.io.*;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class ArrayMax {

    static class IndexMinPQ<Key extends Comparable<Key>> {

        private int maxN;        // maximum number of elements on PQ
        private int n;           // number of elements on PQ
        private int[] pq;        // binary heap using 1-based indexing
        private int[] qp;        // inverse of pq - qp[pq[i]] = pq[qp[i]] = i
        private Key[] keys;      // keys[i] = priority of i


        public IndexMinPQ(int maxN) {
            if (maxN < 0) {
                throw new IllegalArgumentException();
            }
            this.maxN = maxN;
            n = 0;
            keys = (Key[]) new Comparable[maxN + 1];    // make this of length maxN??
            pq = new int[maxN + 1];
            qp = new int[maxN + 1];                   // make this of length maxN??
            for (int i = 0; i <= maxN; i++) {
                qp[i] = -1;
            }
        }


        public boolean contains(int i) {
            validateIndex(i);
            return qp[i] != -1;
        }

    
        public void insert(int i, Key key) {
            validateIndex(i);
            if (!contains(i)) { //allows you to update value of index
                n++;
                qp[i] = n;
                pq[n] = i;
                keys[i] = key;
                swim(n);
            } else {
                changeKey(i, key);
            }

          
        }

  
        public int minIndex() {
            if (n == 0) {
                throw new NoSuchElementException("Priority queue underflow");
            }

            return pq[1];
        }

     
        public void changeKey(int i, Key key) {
            validateIndex(i);
            if (!contains(i)) {
                throw new NoSuchElementException("index is not in the priority queue");
            }
            keys[i] = key;
            swim(qp[i]);
            sink(qp[i]);
        }

        // throw an IllegalArgumentException if i is an invalid index
        private void validateIndex(int i) {
            if (i < 0) {
                throw new IllegalArgumentException("index is negative: " + i);
            }
            if (i >= maxN) {
                throw new IllegalArgumentException("index >= capacity: " + i);
            }
        }

     
        private boolean greater(int i, int j) {

            if (keys[pq[i]] == null) {
                return false;
            }
            if (keys[pq[j]] == null) {
                return true;
            }

            int comparison = keys[pq[i]].compareTo(keys[pq[j]]);

            if (comparison < 0) { //if parent value less than child value
                return true;
            } else if (comparison == 0) { //if parent and child same value
                if (pq[i] > pq[j]) { //if child has smaller index want to swap so true
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }

        private void exch(int i, int j) {
            int swap = pq[i];
            pq[i] = pq[j];
            pq[j] = swap;
            qp[pq[i]] = i;
            qp[pq[j]] = j;
        }

       
        private void swim(int k) {
            while (k > 1 && greater(k / 2, k)) {
                exch(k, k / 2);
                k = k / 2;
            }
        }

        private void sink(int k) {
            while (2 * k <= n) {
                int j = 2 * k;
                if (j < n && greater(j, j + 1)) {
                    j++;
                }
                if (!greater(k, j)) {
                    break;
                }
                exch(k, j);
                k = j;
            }
        }
    }

    public static void main(String[] args) throws IOException {

        
        PrintWriter out = new PrintWriter(System.out);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int M = Integer.parseInt(st.nextToken()),
                N = Integer.parseInt(st.nextToken());
        int[] a = new int[M]; // initially all zero

        IndexMinPQ<Integer> idxMin_pq = new IndexMinPQ<Integer>(M);
        idxMin_pq.insert(0, 0);

        for (int n = 0; n < N; ++n) {
            st = new StringTokenizer(br.readLine());
            int i = Integer.parseInt(st.nextToken()),
                    v = Integer.parseInt(st.nextToken());
            a[i] = v;
            idxMin_pq.insert(i, v);

            int headIndex = idxMin_pq.minIndex();
            out.println(headIndex);

        }
        out.close();
    }
}
