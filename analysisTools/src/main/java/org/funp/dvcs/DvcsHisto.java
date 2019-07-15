package org.funp.dvcs;
//---- imports for HIPO4 library
import org.jlab.jnp.hipo4.io.*;
import org.jlab.jnp.hipo4.data.*;
//---- imports for GROOT library
import org.jlab.groot.data.*;
import org.jlab.groot.graphics.*;
//---- imports for PHYSICS library
import org.jlab.jnp.physics.*;
import org.jlab.jnp.reader.*;

//import org.funp.dvcs.DvcsEvent;;

public class DvcsHisto {
    public H1F W;
    public H1F Q2;
    public H1F MMass;

    public DvcsHisto() {
      W= new H1F("W" ,100, 0, 10.0);
      W.setTitleX("W [GeV]");
      Q2 = new H1F("Q2",100, 0.1, 4.0);
      Q2.setTitleX("Q^2 [GeV/c^2]");
      MMass = new H1F("MMass",100,-30,30);
      MMass.setTitleX("Missing Mass Squared");

      //System.out.println("creating histograms"  );
    }
    public void fillBasicHisto(DvcsEvent ev) {
      W.fill(ev.W().mass());
      Q2.fill(-ev.Q().mass2());
      MMass.fill(ev.MM2());

    }

}
