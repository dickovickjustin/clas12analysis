package org.funp;
import org.jlab.groot.ui.TCanvas;
//---- imports for HIPO4 library
import org.jlab.jnp.hipo4.io.*;
import org.jlab.jnp.hipo4.data.*;
//---- imports for GROOT library
import org.jlab.groot.data.*;
import org.jlab.groot.graphics.*;
//---- imports for PHYSICS library
import org.jlab.jnp.physics.*;
import org.jlab.jnp.reader.*;
import org.funp.dvcs.*;
/**
* testing LorentzVector
*
*/
public class DcoDe
{
  public static void main( String[] args )
  {
    int user=0;

    HipoReader reader = new HipoReader(); // Create a reader obejct
    //reader.open("/home/jnp/data/out_6489_2xx_3xx.hipo"); // open a file
    if(user==1)
    reader.open("/home/justind/DATA/out_6489_2xx_3xx.hipo"); // open a file
    else
    reader.open("/Users/biselli/Data/clas12/rgB/v8hipo4/out_6489_2xx.hipo"); // open a file
    Event     event = new Event();
    Bank  particles = new Bank(reader.getSchemaFactory().getSchema("REC::Particle"));
    Bank  run       = new Bank(reader.getSchemaFactory().getSchema("REC:Event"));
    DvcsEvent ev    = new DvcsEvent();
    DvcsHisto h     = new DvcsHisto();
    reader.getEvent(event,0); //Reads the first event and resets to the begining of the file
    int times=0;

    //LorentzVector  vtmp = new LorentzVector();


    LorentzVector  vMMass = new LorentzVector();
    LorentzVector  vMMom = new LorentzVector();
    Particle  proton = new Particle();
    Particle neutron = new Particle();
    Particle deuteron = new Particle();
    Particle electron = new Particle();
    Particle photon = new Particle();
    double Xbj;

    int ndvcs=0;

    int counter=100;

    //double MNUC=1.878;
    // histos

    //H1F W = new H1F("W" ,100, 0, 10.0);
    //W.setTitleX("W [GeV]");
    //H1F Q2 = new H1F("Q2",100, 0.1, 4.0);
    //Q2.setTitleX("Q^2 [GeV/c^2]");
    H1F MMass = new H1F("MMass",100,-30,30);
    MMass.setTitleX("Missing Mass Squared");
    H1F MMom = new H1F("MMass",100,-30,30);
    MMom.setTitleX("Missing Momentum");
    H1F MMomx = new H1F("MMass",100,-10,10);
    MMomx.setTitleX("Missing X Momentum");
    H1F MMomy = new H1F("MMass",100,-10,10);
    MMomy.setTitleX("Missing Y Momentum");
    H1F MMomz = new H1F("MMass",100,-10,15);
    MMomz.setTitleX("Missing Z Momentum");
    H2F WvsQ2 = new H2F("W vs Q2", "W vs Q2", 100,0,7,100,0,10);
    WvsQ2.setTitleX("W [GeV]");
    WvsQ2.setTitleY("Q^2 [GeV/c^2]");
    H2F ThvsPhi = new H2F("Theta vs Phi","Theta vs Phi",100,-180,180,100,0,180);
    ThvsPhi.setTitleX("Phi [Degrees]");
    ThvsPhi.setTitleY("Theta [Degrees]");
    H2F Q2vsXbj = new H2F("Q2 vs Xbj","Q2 vs Xbj",100,0,10,100,0,1);
    Q2vsXbj.setTitleX("Q^2 [GeV/c^2]");
    Q2vsXbj.setTitleY("Xbj");
    H2F MMvsMpz = new H2F("Q2 vs Xbj","Q2 vs Xbj",100,-2,2,100,-10,10);
    MMvsMpz.setTitleX("Missing Mass");
    MMvsMpz.setTitleY("Missing Z Momentum");
    H2F MpxvsMpz = new H2F("Q2 vs Xbj","Q2 vs Xbj",100,-2,2,100,-10,10);
    MpxvsMpz.setTitleX("Missing X Momentum");
    MpxvsMpz.setTitleY("Missing Z Momentum");
    H1F ThetaHist = new H1F("ThetaHist",100,0,50);
    ThetaHist.setTitle("Photon Theta");
    H1F DThetaHist = new H1F("DThetaHist",100,0,50);
    DThetaHist.setTitle("DTheta");
    H1F MissThetaHist = new H1F("MissThetaHist",100,0,180);
    MissThetaHist.setTitle("Missing Photon Theta");
    H1F PhiPlaneHist = new H1F("PhiPlaneHist",100,0,180);
    PhiPlaneHist.setTitle("Photon Phi Plane");
    H1F DPhiHist = new H1F("DPhiHist",100,-180,180);
    DPhiHist.setTitle("DPhi");
    H1F DeltaPhiPlaneHist = new H1F("DeltaPhiPlane",100,0,360);
    DeltaPhiPlaneHist.setTitle("Delta Phi Plane");
    //loop over the events
    while(reader.hasNext()==true){
      reader.nextEvent(event);
      event.read(particles);

      if(ev.FilterParticles(particles)){

        DeltaPhiPlaneHist.fill(ev.deltaPhiPlane());
        PhiPlaneHist.fill(ev.PhiPlane());

        if(ev.DVCScut()){
          ndvcs++;
          ThvsPhi.fill(Math.toDegrees(ev.vhadron.phi()),Math.toDegrees(ev.vhadron.theta()));
          Xbj=ev.Xb();
          Q2vsXbj.fill(-ev.Q().mass2(),Xbj);

          ThetaHist.fill(Math.toDegrees(ev.vphoton.theta()));
          DThetaHist.fill(ev.DTheta());
          //vtmp.copy(ev.vBeam);
          //vtmp.add(ev.vTarget);
          //vtmp.sub(ev.velectron).sub(ev.vhadron);
          MissThetaHist.fill(Math.toDegrees(ev.ehehX().theta()));

          PhiPlaneHist.fill(ev.PhiPlane());
          //DPhiHist.fill(ev.DPhi());
          DeltaPhiPlaneHist.fill(ev.deltaPhiPlane());

          //W.fill(ev.W().mass());
          //Q2.fill(-ev.Q().mass2());
          WvsQ2.fill(ev.W().mass(),-ev.Q().mass2());

          MMass.fill(ev.MM2());

          //if(vMMass.mass2()>-1 && vMMass.mass2()<1 && (vphoton.theta()*180./Math.PI)<5){

          //    MMom.fill(vMMom.p());
          h.fillBasicHisto(ev);

          MMomx.fill(ev.Mpx());
          MMomy.fill(ev.Mpy());
          MMomz.fill(ev.Mpz());

          //    MMvsMpz.fill(vMMass.mass2(),vMMom.pz());
          //    MpxvsMpz.fill(vMMom.px(),vMMom.pz());
          //}
        }
      }

    }
    //if(counter==0)break;
    //counter--;

    System.out.println("total dvcs events: " + ndvcs);
    TCanvas ec = new TCanvas("c",1200,1000);
    ec.divide(3,3);
    //ec.getPad(0).getAxisZ().setLog(true);
    ec.cd(0).draw(WvsQ2);
    //ec.getPad(1).getAxisZ().setLog(true);
    ec.cd(1).draw(Q2vsXbj);
    ec.cd(2).draw(h.W);
    ec.cd(3).draw(h.Q2);
    ec.cd(4).draw(ThvsPhi);
    ec.cd(5).draw(MMomx);
    ec.cd(6).draw(MMomy);
    ec.cd(7).draw(MMomz);
    ec.getCanvas().getScreenShot();
    ec.getCanvas().save("test.png");



  }
}
