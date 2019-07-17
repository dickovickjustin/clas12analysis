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
    public H1F MMom;
    public H1F MMomx;
    public H1F MMomy;
    public H1F MMomz;
    public H2F WvsQ2;
    public H2F ThvsPhi;
    public H2F Q2vsXbj;
    public H2F MMvsMpz;
    public H2F MpxvsMpz;
    public H1F ThetaHist;
    public H1F DThetaHist ;
    public H1F MissThetaHist;
    public H1F PhiPlaneHist ;
    public H1F DPhiHist ;
    public H1F DeltaPhiPlaneHist;

    public DvcsHisto() {
      W= new H1F("W" ,100, 0, 10.0);
      W.setTitleX("W [GeV]");
      Q2 = new H1F("Q2",100, 0.1, 4.0);
      Q2.setTitleX("Q^2 [GeV/c^2]");
      MMass = new H1F("MMass",100,-30,30);
      MMass.setTitleX("Missing Mass Squared");


      MMom = new H1F("MMass",100,-30,30);
      MMom.setTitleX("Missing Momentum");
      MMomx = new H1F("MMass",100,-10,10);
      MMomx.setTitleX("Missing X Momentum");
      MMomy = new H1F("MMass",100,-10,10);
      MMomy.setTitleX("Missing Y Momentum");
      MMomz = new H1F("MMass",100,-10,15);
      MMomz.setTitleX("Missing Z Momentum");
      WvsQ2 = new H2F("W vs Q2", "W vs Q2", 100,0,7,100,0,10);
      WvsQ2.setTitleX("W [GeV]");
      WvsQ2.setTitleY("Q^2 [GeV/c^2]");
      ThvsPhi = new H2F("Theta vs Phi","Theta vs Phi",100,-180,180,100,0,180);
      ThvsPhi.setTitleX("Phi [Degrees]");
      ThvsPhi.setTitleY("Theta [Degrees]");
      Q2vsXbj = new H2F("Q2 vs Xbj","Q2 vs Xbj",100,0,10,100,0,1);
      Q2vsXbj.setTitleX("Q^2 [GeV/c^2]");
      Q2vsXbj.setTitleY("Xbj");
      MMvsMpz = new H2F("Q2 vs Xbj","Q2 vs Xbj",100,-2,2,100,-10,10);
      MMvsMpz.setTitleX("Missing Mass");
      MMvsMpz.setTitleY("Missing Z Momentum");
      MpxvsMpz = new H2F("Q2 vs Xbj","Q2 vs Xbj",100,-2,2,100,-10,10);
      MpxvsMpz.setTitleX("Missing X Momentum");
      MpxvsMpz.setTitleY("Missing Z Momentum");
      ThetaHist = new H1F("ThetaHist",100,0,50);
      ThetaHist.setTitle("Photon Theta");
      DThetaHist = new H1F("DThetaHist",100,0,50);
      DThetaHist.setTitle("DTheta");
      MissThetaHist = new H1F("MissThetaHist",100,0,180);
      MissThetaHist.setTitle("Missing Photon Theta");
      PhiPlaneHist = new H1F("PhiPlaneHist",100,0,180);
      PhiPlaneHist.setTitle("Photon Phi Plane");
      DPhiHist = new H1F("DPhiHist",100,-180,180);
      DPhiHist.setTitle("DPhi");
      DeltaPhiPlaneHist = new H1F("DeltaPhiPlane",100,0,360);
      DeltaPhiPlaneHist.setTitle("Delta Phi Plane");

      //System.out.println("creating histograms"  );
    }
    public void fillBasicHisto(DvcsEvent ev) {
      W.fill(ev.W().mass());
      Q2.fill(-ev.Q().mass2());
      MMass.fill(ev.MM2());
      DeltaPhiPlaneHist.fill(ev.deltaPhiPlane());
      PhiPlaneHist.fill(ev.PhiPlane());
      ThvsPhi.fill(Math.toDegrees(ev.vhadron.phi()),Math.toDegrees(ev.vhadron.theta()));
      //Xbj=ev.Xb();
      Q2vsXbj.fill(-ev.Q().mass2(),ev.Xb());

      ThetaHist.fill(Math.toDegrees(ev.vphoton.theta()));
      DThetaHist.fill(ev.DTheta());
      //vtmp.copy(ev.vBeam);
      //vtmp.add(ev.vTarget);
      //vtmp.sub(ev.velectron).sub(ev.vhadron);
      MissThetaHist.fill(Math.toDegrees(ev.X("eh").theta()));

      PhiPlaneHist.fill(ev.PhiPlane());
      //DPhiHist.fill(ev.DPhi());
      DeltaPhiPlaneHist.fill(ev.deltaPhiPlane());

      //W.fill(ev.W().mass());
      //Q2.fill(-ev.Q().mass2());
      WvsQ2.fill(ev.W().mass(),-ev.Q().mass2());

      MMass.fill(ev.MM2());
      MMomx.fill(ev.Mpx());
      MMomy.fill(ev.Mpy());
      MMomz.fill(ev.Mpz());


    }

}
