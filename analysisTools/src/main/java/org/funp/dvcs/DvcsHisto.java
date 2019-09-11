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

import org.jlab.groot.ui.TCanvas;

//import org.funp.dvcs.DvcsEvent;;

public class DvcsHisto {
  public H1F W; //invariant mass of e target -> e' X
  public H1F Q2;//Momentum transfer squared  of e-e'
  public H2F WvsQ2;
  public H2F Q2vsXbj;
  //Missing quantities
  public H1F MMass;// missing mass of a complete DVCS final state e hadron gamma
  public H1F MMom;// missing mom of a complete DVCS final state e hadron gamma
  public H1F MMomx;// missing px of a complete DVCS final state e hadron gamma
  public H1F MMomy;// missing py of a complete DVCS final state e hadron gamma
  public H1F MMomz;// missing pz of a complete DVCS final state e hadron gamma

  public H1F edgXmissingE; // missing mass of a complete DVCS final state e hadron gamma
  public H1F edgXmissingM2 ; // missing mass of a complete DVCS final state e hadron gamma
  public H1F edgXmissingP ; // missing mass of a complete DVCS final state e hadron gamma

  public H1F edXmissingM2; // missing mass of hadron electron final state (to be compared with gamma)
  public H1F egXmissingM2; // missing mass of gamma electron final state (to be compared with hadron)


  public H2F ThvsPhi;//Theta vs phi for hadron

  //public H2F MMvsMpz;
  //public H2F MpxvsMpz;

  public H1F ThetaHist;//theta gamma
  public H1F DAngleGammaHist ; //angle between gamma vector and missing hadron+e vector
  public H1F ConeAngleHist;//angle between gamma vector and missing hadron+e vector
  public H1F MissThetaHist;//theta missing hadron+e vector
  public H1F PhiPlaneHist ; //angle between electrons plane and hadron/gamma plane
  public H1F DPhiHist ;//phi gamma minus phi missing hadron+e vector
  public H1F DeltaPhiPlaneHist; //angle planes Q2/hadron and gamma/hadrom
  public H1F DeltaPhiPlaneMattHist;//angle planes Q2/hadron and Q2/gamma

  public H2F coneanglevsedgXM2;//angle between gamma vector and missing hadron+e vector vs missin mass square ehgX
  public H2F coneanglevsedXM2;//angle between gamma vector and missing hadron+e vector vs missin mass square ehX


  public DvcsHisto() {
    W= new H1F("W" ,100, 0, 10.0);
    W.setTitleX("W [GeV]");
    Q2 = new H1F("Q2",100, 0.1, 4.0);
    Q2.setTitleX("Q^2 [GeV/c^2]");
    WvsQ2 = new H2F("W vs Q2", "W vs Q2", 100,0,7,100,0,10);
    WvsQ2.setTitleX("W [GeV]");
    WvsQ2.setTitleY("Q^2 [GeV/c^2]");
    Q2vsXbj = new H2F("Q2 vs Xbj","Q2 vs Xbj",100,0,10,100,0,1);
    Q2vsXbj.setTitleX("Q^2 [GeV/c^2]");
    Q2vsXbj.setTitleY("Xbj");

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

    edgXmissingE = new H1F("edgXmissingE",100,-30,30);
    edgXmissingE.setTitle("eDGammaX Missing Energy");
    edgXmissingM2 = new H1F("edgXmissingM2",100,-30,30);
    edgXmissingM2.setTitle("eDGammaX Missing Mass2");
    edgXmissingP = new H1F("edgXmissingP",100,0,30);
    edgXmissingP.setTitle("eDGammaX Missing Momentum");

    edXmissingM2 = new H1F("edXmissingM2",100,-30,30);
    edXmissingM2.setTitle("eDX Missing Mass2");
    egXmissingM2 = new H1F("egXmissingM2",100,-30,30);
    egXmissingM2.setTitle("eGammaX Missing Mass2");

    ThvsPhi = new H2F("Theta vs Phi","Theta vs Phi",100,-180,180,100,0,180);
    ThvsPhi.setTitleX("Phi [Degrees]");
    ThvsPhi.setTitleY("Theta [Degrees]");

    //MMvsMpz = new H2F("Q2 vs Xbj","Q2 vs Xbj",100,-2,2,100,-10,10);
    //MMvsMpz.setTitleX("Missing Mass");
    //MMvsMpz.setTitleY("Missing Z Momentum");
    //MpxvsMpz = new H2F("Q2 vs Xbj","Q2 vs Xbj",100,-2,2,100,-10,10);
    //MpxvsMpz.setTitleX("Missing X Momentum");
    //MpxvsMpz.setTitleY("Missing Z Momentum");
    ThetaHist = new H1F("ThetaHist",100,0,50);
    ThetaHist.setTitle("Photon Theta");
    DAngleGammaHist = new H1F("DAngleGammaHist",100,0,50);
    DAngleGammaHist.setTitle("Angle between gamma and missing eDX");
    ConeAngleHist = new H1F("ConeAngleHist",100,-15,180);
    ConeAngleHist.setTitle("Cone Angle");
    MissThetaHist = new H1F("MissThetaHist",100,0,180);
    MissThetaHist.setTitle("Missing Photon Theta");
    PhiPlaneHist = new H1F("PhiPlaneHist",100,0,180);
    PhiPlaneHist.setTitle("Photon Phi Plane");
    DPhiHist = new H1F("DPhiHist",100,-180,180);
    DPhiHist.setTitle("DPhi");
    DeltaPhiPlaneHist = new H1F("DeltaPhiPlane",100,0,360);
    DeltaPhiPlaneHist.setTitle("Delta Phi Plane");
    DeltaPhiPlaneMattHist = new H1F("DeltaPhiPlane",100,-50,50);
    DeltaPhiPlaneMattHist.setTitle("Delta Phi Plane Hattawy");
  

    coneanglevsedgXM2 = new H2F("Cone Angle vs eDGammaX missing M2","Cone Angle vs eDGammaX missing M2",100,0,180,100,-30,30);
    coneanglevsedgXM2.setTitleX("Cone Angle (deg.)");
    coneanglevsedgXM2.setTitleY("eDGammaX missing M2 (GeV)");
    coneanglevsedXM2 = new H2F("Cone Angle vs eDX missing M2","Cone Angle vs eDX missing M2",100,0,180,100,-30,30);
    coneanglevsedXM2.setTitleX("Cone Angle (deg.)");
    coneanglevsedXM2.setTitleY("eDX missing M2 (GeV)");

    //System.out.println("creating histograms"  );
  }
  public void fillBasicHisto(DvcsEvent ev) {
    W.fill(ev.W().mass());
    Q2.fill(-ev.Q().mass2());
    WvsQ2.fill(ev.W().mass(),-ev.Q().mass2());
    Q2vsXbj.fill(-ev.Q().mass2(),ev.Xb());

    //missing quantities of a complete DVCS final state e hadron gamma
    MMass.fill(ev.MM2());
    MMom.fill(ev.Mp());
    MMomx.fill(ev.Mpx());
    MMomy.fill(ev.Mpy());
    MMomz.fill(ev.Mpz());

    edgXmissingE.fill(ev.X("ehg").e());
    edgXmissingM2.fill(ev.X("ehg").mass2());
    edgXmissingP.fill(ev.X("ehg").p());

    edXmissingM2.fill(ev.X("eh").mass2());
    egXmissingM2.fill(ev.X("eg").mass2());

    ThvsPhi.fill(Math.toDegrees(ev.vhadron.phi()),Math.toDegrees(ev.vhadron.theta()));
//Xbj=ev.Xb();

    ThetaHist.fill(Math.toDegrees(ev.vphoton.theta()));
    DAngleGammaHist.fill(ev.DTheta());
    ConeAngleHist.fill(ev.coneangle());
    MissThetaHist.fill(Math.toDegrees(ev.X("eh").theta()));
    PhiPlaneHist.fill(ev.PhiPlane());
    DPhiHist.fill(ev.DPhi());
    DeltaPhiPlaneHist.fill(ev.deltaPhiPlane());
    DeltaPhiPlaneMattHist.fill(ev.deltaPhiPlane2());

    coneanglevsedgXM2.fill(ev.coneangle(),ev.X("egh").mass2());
    coneanglevsedXM2.fill(ev.coneangle(),ev.X("eh").mass2());

  }
  public void DrawMissing(TCanvas ec4){

    ec4.divide(3,3);
    ec4.cd(0).draw(DeltaPhiPlaneHist);
    ec4.cd(1).draw(edgXmissingE);
    ec4.cd(2).draw(edgXmissingM2);
    ec4.cd(3).draw(edgXmissingP);
    ec4.cd(4).draw(edXmissingM2);
    ec4.cd(5).draw(egXmissingM2);
    ec4.cd(6).draw(ConeAngleHist);
    ec4.cd(7).draw(MMomx);
    ec4.cd(8).draw(MMomy);
    //ec4.getScreenShot();


  }
}
