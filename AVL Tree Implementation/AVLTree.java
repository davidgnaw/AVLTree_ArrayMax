// THIS CODE IS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING
// A TUTOR OR CODE WRITTEN BY OTHER STUDENTS - YU FUNG DAVID WANG


public class AVLTree<K extends Comparable<K>> extends BST<K>
{
    

    protected Node fixup(Node t)
    {
        t.update();             // update t.size and t.height

        int bal = height(t.left) - height(t.right);
        if (bal >= -1 && bal <= +1)
            // got lucky: balanced already, nothing to do!
            return t;

        else if (bal < -1) { //right height > left height
            if (height(t.right.left) - height(t.right.right) > 0) { //check the subtree to the right
                t.right = rotateRight(t.right); //double rotation (right-left case)
                return rotateLeft(t); //right-right case
            } else {
                return rotateLeft(t); //single left rotation
            }
        } 

        else if (bal > +1) { //left height > right height
            if (height(t.left.right) - height(t.left.left) > 0) {
                t.left = rotateLeft(t.left); //double rotation (left-right case)
                return rotateRight(t); //left-left case
            } else {
                return rotateRight(t); //single right rotation
            }
        }

        return t;
    }

    Node rotateRight(Node k2) {
        Node k1 = k2.left;
        k2.left = k1.right;
        k2.update(); // update size and height
        k1.right = k2;
        k1.update(); // update size and height
        return k1;   // return new root of this subtreee
    }

    Node rotateLeft(Node k2) {
        Node k1 = k2.right;
        k2.right = k1.left;
        k2.update();
        k1.left = k2;
        k1.update();
        return k1;

        
    }
}
