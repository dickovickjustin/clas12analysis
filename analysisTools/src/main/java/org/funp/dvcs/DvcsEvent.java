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

import java.util.Comparator;
//import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DvcsEvent {
    public LorentzVector  vBeam   = new LorentzVector(0.0,0.0,10.2,10.2);
    public LorentzVector  vTarget = new LorentzVector(0.0,0.0,0.0,1.878);
    public LorentzVector  velectron = new LorentzVector();
    public LorentzVector  vphoton = new LorentzVector();
    public LorentzVector  vhadron = new LorentzVector();
    double MNUC=1.878;
    double el_en_max=0;
    double ph_en_max=0;
    double d_en_max=0;
    int PIDNUC=45;
    int nelec=0;
    int nphot=0;
    int ndeut=0;
    int ne=-1;
    int ng=-1;
    int nd=-1;
    boolean FoundEvent= false;
    boolean NewEvent=false;

    public DvcsEvent() {
      // This constructor no parameter.
      //System.out.println("setting the default DVCS event for hadron :" + MNUC );
   }
    public DvcsEvent(double mass) {
      // This constructor no parameter.
      MNUC=mass;
      System.out.println("setting the default DVCS event for hadron :" + MNUC );
   }

   public void setElectron(Bank particles, int ne) {
      velectron.setPxPyPzM(particles.getFloat("px",ne),
                                 particles.getFloat("py",ne),
                                 particles.getFloat("pz",ne),
                                 0.0005);
   }

   public void setPhoton(Bank particles, int ng) {
      vphoton.setPxPyPzM( particles.getFloat("px",ng),
                                particles.getFloat("py",ng),
                                particles.getFloat("pz",ng),
                                0.0);
   }
   public void setHadron(Bank particles, int nh) {
      vhadron.setPxPyPzM(particles.getFloat("px",nh),
                                particles.getFloat("py",nh),
                                particles.getFloat("pz",nh),
                                this.MNUC);
   }
   public  boolean FilterParticles(Bank particles) {
       LorentzVector  vtmp = new LorentzVector();
       FoundEvent= false;
       this.el_en_max=0;
       this.ph_en_max=0;
       this.d_en_max=0;

       nelec=0;
       nphot=0;
       ndeut=0;
       ne=-1;
       ng=-1;
       nd=-1;

       if(particles.getRows()>3){
        for(int npart=0; npart<particles.getRows(); npart++){
            int pid = particles.getInt("pid", npart);
            if(pid==11){
                nelec++;
                vtmp.setPxPyPzM(particles.getFloat("px",npart),
                                     particles.getFloat("py",npart),
                                     particles.getFloat("pz",npart),
                                     0.0005);
                if(vtmp.e()>this.el_en_max)ne=npart;
            }
            else if(pid==22){
                nphot++;
                vtmp.setPxPyPzM(particles.getFloat("px",npart),
                                     particles.getFloat("py",npart),
                                     particles.getFloat("pz",npart),
                                     0.0);
                if(vtmp.e()>this.ph_en_max)ng=npart;

            }
            else if(pid==PIDNUC){
                ndeut++;
                vtmp.setPxPyPzM(particles.getFloat("px",npart),
                                     particles.getFloat("py",npart),
                                     particles.getFloat("pz",npart),
                                     this.MNUC);
                if(vtmp.e()>this.d_en_max)nd=npart;
            }
        }
        if( ndeut>=1 && nelec>=1 && nphot>=1){
            this.setElectron(particles,ne);
            this.setPhoton(particles,ng);
            this.setHadron(particles,nd);
            FoundEvent=true;
          }
       }
       NewEvent=true;
       return FoundEvent;
   }
   public LorentzVector W(){
       LorentzVector  tmp = new LorentzVector();
       tmp.copy(vBeam);
       tmp.add(vTarget).sub(velectron);
       return tmp;

   }
   public LorentzVector Q(){
       LorentzVector  tmp = new LorentzVector();
       tmp.copy(vBeam);
       tmp.sub(velectron);
       return tmp;

   }
   public LorentzVector DVCSmissX(){
       LorentzVector  tmp = new LorentzVector();
       tmp.copy(vBeam);
       tmp.add(vTarget);
       tmp.sub(velectron);
       tmp.sub(vphoton);
       return tmp;

   }
   public LorentzVector ehehgX(){
       LorentzVector  tmp = new LorentzVector();
       tmp.copy(vBeam);
       tmp.add(vTarget);
       tmp.sub(velectron);
       tmp.sub(vphoton);
       tmp.sub(vhadron);
       return tmp;
   }
   public LorentzVector ehehX(){
       LorentzVector  tmp = new LorentzVector();
       tmp.copy(vBeam);
       tmp.add(vTarget);
       tmp.sub(velectron);
       tmp.sub(vhadron);
       return tmp;
   }
   public double MM2(){
       return this.X("egh").mass2();
   }
   public double Mpx(){
       return this.X("egh").px();
   }
   public double Mpy(){
       return this.X("egh").py();
   }
   public double Mpz(){
       return this.X("egh").pz();
   }
   public boolean DVCScut(){
       boolean cut=(-this.Q().mass2()>1 && this.W().mass()>2 && this.vphoton.e()>1);
       return cut;
   }
   public double Xb(){
       return (-this.Q().mass2())/(2*0.938*(this.vBeam.p()-this.velectron.p()));
   }
   public double DTheta(){
        LorentzVector temp = new LorentzVector();
        temp.copy(this.X("eh"));
    return Math.toDegrees(vphoton.vect().angle(temp.vect()));
   }
   public double DPhi(){
        LorentzVector temp = new LorentzVector();
        temp.copy(this.X("eh"));
    return vphoton.vect().phi() - temp.vect().phi();
   }
   public double PhiPlane(){
       double Phi;
        Vector3 leptonicPlane = new Vector3();
            leptonicPlane.copy(vBeam.vect().cross(velectron.vect()));
        Vector3 hadronicPlane = new Vector3();
            hadronicPlane.copy(vhadron.vect().cross(vphoton.vect()));
        Phi = Math.toDegrees(leptonicPlane.angle(hadronicPlane));
       //System.out.println("Angle = " + Phi);
       return Phi;
   }
    public double deltaPhiPlane(){
        double deltaphi;
        LorentzVector tmp=new LorentzVector();
        tmp.copy(vBeam);
        tmp.sub(velectron);
        Vector3 norm_Pro_VPho = (vhadron.vect().cross(tmp.vect()));
        Vector3 norm_Pro_Pho = (vhadron.vect().cross(vphoton.vect()));
        deltaphi = Math.toDegrees(norm_Pro_Pho.angle(norm_Pro_VPho));
        if(norm_Pro_VPho.dot(vphoton.vect()) < 0 ) deltaphi = -1*deltaphi;
        return deltaphi;
    }
    //this function returns the missing vector for a given list of possible particles in a dvcs events
    //could be ehg or eg eh
    public LorentzVector X(String listpart){
      //System.out.println(listpart);
      String newlistpart=Stream.of(listpart).sorted(Comparator.comparingInt(o -> Character.toLowerCase(o.charAt(0)))).collect(Collectors.joining());
        //System.out.println(newlistpart);

        LorentzVector  tmp = new LorentzVector();
        tmp.copy(vBeam);
        tmp.add(vTarget);
        tmp.sub(velectron);
        if(listpart.equals("egh")){
          tmp.sub(vphoton);
          tmp.sub(vhadron);
        }
        else if(listpart.equals("eg")){
          tmp.sub(vphoton);

        }
        else if(listpart.equals("eh")){
          tmp.sub(vhadron);
        }
        else {
          System.out.println(listpart+" combination of particle to calculate the missing particle is not supported, use e,g,h" );
        }
      return tmp;
    }
}
