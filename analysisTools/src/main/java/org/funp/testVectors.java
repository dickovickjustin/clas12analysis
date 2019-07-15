package org.funp;
//---- imports for GROOT library
import org.jlab.groot.data.*;
import org.jlab.groot.graphics.*;
//---- imports for PHYSICS library
import org.jlab.jnp.physics.*;
import org.jlab.jnp.reader.*;

/**
 * testing LorentzVector
 *
 */
public class testVectors
{
    public static void main( String[] args )
    {
        LorentzVector v1=new LorentzVector();
        LorentzVector v2=new LorentzVector();
        v1.setPxPyPzM(1, 0, 0, 0.5);
        v2.setPxPyPzM(1, 1, 0, 0.5);

        System.out.println( v1.vect().angle(v2.vect()) );
    }
}
