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
    reader.open("/Users/biselli/Data/clas12/rgB/pass0v15/out_6595_2xx-3xx.hipo"); // open a file
    //reader.open("/Users/biselli/Data/clas12/rgB/v8hipo4/out_6489_2xx.hipo"); // open a file
    Event     event = new Event();
    Bank  particles = new Bank(reader.getSchemaFactory().getSchema("REC::Particle"));
    Bank  run       = new Bank(reader.getSchemaFactory().getSchema("REC:Event"));
    DvcsEvent ev    = new DvcsEvent();
    DvcsHisto hNC     = new DvcsHisto();
    DvcsHisto hDC     = new DvcsHisto();
    DvcsHisto hAC     = new DvcsHisto();

    reader.getEvent(event,0); //Reads the first event and resets to the begining of the file
    int times=0;

    int ndvcs=0;

    int counter=0;


    //loop over the events
    while(reader.hasNext()==true){
      reader.nextEvent(event);
      event.read(particles);
      if(ev.FilterParticles(particles)){
        hNC.fillBasicHisto(ev);
        if(ev.DVCScut()){
          ndvcs++;
          //if(vMMass.mass2()>-1 && vMMass.mass2()<1 && (vphoton.theta()*180./Math.PI)<5){
          //    MMom.fill(vMMom.p());
          hDC.fillBasicHisto(ev);
          if((Math.toDegrees(ev.vphoton.theta())<5)  && ev.coneangle()<3 && ev.MM2()<15){
            hAC.fillBasicHisto(ev);
            counter++;
          }
        }
      }
    }
    //if(counter==0)break;
    //counter--;

    System.out.println("total dvcs events: " + ndvcs);
    TCanvas ec = new TCanvas("c",1200,1000);
    ec.divide(3,3);
    //ec.getPad(0).getAxisZ().setLog(true);
    ec.cd(0).draw(hNC.WvsQ2);
    //ec.getPad(1).getAxisZ().setLog(true);
    ec.cd(1).draw(hNC.Q2vsXbj);
    ec.cd(2).draw(hNC.W);
    ec.cd(3).draw(hNC.Q2);
    ec.cd(4).draw(hNC.ThvsPhi);
    ec.cd(5).draw(hNC.MMomx);
    ec.cd(6).draw(hNC.MMomy);
    ec.cd(7).draw(hNC.MMomz);
    ec.getCanvas().getScreenShot();
    ec.getCanvas().save("test.png");

    TCanvas ec4 = new TCanvas("c2",1500,1500);
    hDC.DrawMissing(ec4);

    TCanvas ec5 = new TCanvas("c3",1500,1500);
    hAC.DrawMissing(ec5);


  }
}
