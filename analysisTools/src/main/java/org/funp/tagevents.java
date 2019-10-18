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

import java.util.*;
import java.io.*;

public class tagevents
{
  public static void main( String[] args )
  {
    int ndeut=0;
    int nphot=0;
    int nelec=0;
    int totalcounter=0;
    int tagcounter=0;

    HipoReader reader = new HipoReader(); // Create a reader object
    reader.open("/home/justind/DATA/dst_edeut_006596.hipo"); // open a file
    HipoWriterSorted writer = new HipoWriterSorted();
    writer.getSchemaFactory().copy(reader.getSchemaFactory());
    writer.open("/home/justind/DATA/dst_edeut_006596_tagged.hipo");

    Bank  particles = new Bank(reader.getSchemaFactory().getSchema("REC::Particle"));
    Event     event = new Event();

    reader.getEvent(event,0); //Reads the first event and resets to the begining of the file

    while(reader.hasNext()==true){
      reader.nextEvent(event);
      event.read(particles);
      totalcounter++;
      event.setEventTag(0);
      if(particles.getRows()>0){
        for(int npart=0; npart<particles.getRows(); npart++){
            int pid = particles.getInt("pid", npart);
            if(pid==45){
              ndeut++;
          }
            else if(pid==11){
              nelec++;
            }
            else if(pid==22){
              nphot++;
            }
        }
      if(nphot>=1 && ndeut>=1 && nelec>=1){
          tagcounter++;
          event.setEventTag(10);
      }
    }
    writer.addEvent(event,event.getEventTag());
    }
    writer.close();
    System.out.println("total counter: " + totalcounter);
    System.out.println("tag counter: " + tagcounter);
  }
}
