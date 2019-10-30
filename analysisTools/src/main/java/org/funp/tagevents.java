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
import org.funp.utilities.*;

import java.util.*;
import java.io.*;

public class tagevents
{
  public static void main( String[] args )
  {

    processInput inputParam=new processInput(args);

    int ndeut=0;
    int nphot=0;
    int nelec=0;
    int totalcounter=0;
    int dvcscounter=0;

    HashMap<Integer, Double> hmap=createrunmap();

    for ( int i=0; i<inputParam.getNfiles(); i++) {

      HipoReader reader = new HipoReader(); // Create a reader object
      System.out.println(inputParam.getFileName(i));
      reader.open(inputParam.getFileName(i)); // open a file
      HipoWriterSorted writer = new HipoWriterSorted();
      writer.getSchemaFactory().copy(reader.getSchemaFactory());
      writer.open("/home/justind/DATA/dst_edeut_tagged.hipo");

      Bank  particles = new Bank(reader.getSchemaFactory().getSchema("REC::Particle"));
      Bank  scint     = new Bank(reader.getSchemaFactory().getSchema("REC::Scintillator"));
      Bank  runconfig       = new Bank(reader.getSchemaFactory().getSchema("RUN::config"));
      Event     event = new Event();
      event.read(runconfig);
      event.read(scint);
      DvcsEvent ev    = new DvcsEvent();
      reader.getEvent(event,0); //Reads the first event and resets to the begining of the file

      //map beam energies
      if(hmap.get(runconfig.getInt("run",0))!=null){
        ev.BeamEnergy=hmap.get(runconfig.getInt("run",0));
        ev.vBeam.setPxPyPzE(0, 0, Math.sqrt(ev.BeamEnergy*ev.BeamEnergy-0.0005*0.0005), ev.BeamEnergy);

        System.out.println("Beam energy found for run"+runconfig.getInt("run",0)+" "+ev.vBeam.e());
      }
      else {
        System.out.println("Uknown beam energy for this run setting to default of"+ev.vBeam.e() );
      }


      while(reader.hasNext()==true){
        reader.nextEvent(event);
        event.read(particles);
	event.read(scint);
        totalcounter++;
        event.setEventTag(0);
        if(ev.FilterParticles(particles,scint)){
        if(ev.W().mass() > 2 && -ev.Q().mass2() > 1){
            dvcscounter++;
            event.setEventTag(1);
        }
      }
      writer.addEvent(event,event.getEventTag());
      }
      writer.close();
      System.out.println("total counter: " + totalcounter);
      System.out.println("dvcs counter: " + dvcscounter);
    }
  }

  static HashMap<Integer, Double> createrunmap(){
    HashMap<Integer, Double> hmap = new HashMap<Integer, Double>();
    Double beam10p6=10.5986;
    Double beam10p2=10.1998;
    hmap.put(6302,beam10p6);
    hmap.put(6303,beam10p6);
    hmap.put(6305,beam10p6);
    hmap.put(6307,beam10p6);
    hmap.put(6310,beam10p6);
    hmap.put(6313,beam10p6);
    hmap.put(6321,beam10p6);
    hmap.put(6311,beam10p6);
    hmap.put(6327,beam10p6);
    hmap.put(6346,beam10p6);
    hmap.put(6347,beam10p6);
    hmap.put(6349,beam10p6);

    hmap.put(6428,beam10p2);
    hmap.put(6433,beam10p2);
    hmap.put(6442,beam10p2);
    hmap.put(6450,beam10p2);
    hmap.put(6467,beam10p2);
    hmap.put(6474,beam10p2);
    hmap.put(6481,beam10p2);
    hmap.put(6492,beam10p2);
    return hmap;

  }

}
