package it.polito.tdp.flight.model;

import java.awt.Dialog.ModalExclusionType;
import java.util.Set;

public class TestModel {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Model model= new Model();
		model.creaGrafo(800);
//		for(Set s: model.connetivita()) {
//			if(s.size()>1) {
//			System.out.println(s+"\n");
//			}
//		}
		System.out.println(model.daFiumicino());
		
	}

}
