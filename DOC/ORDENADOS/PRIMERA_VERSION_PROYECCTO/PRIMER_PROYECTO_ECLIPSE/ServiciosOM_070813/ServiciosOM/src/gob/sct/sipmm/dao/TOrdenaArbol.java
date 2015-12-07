package gob.sct.sipmm.dao;

import java.util.*;

import com.micper.seguridad.vo.*;

/**
 * <p>Title: TDOrdenaArea.java</p>
 * <p>Description: Ordenamiento de Arboles</p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Tecnología InRed S.A. de C.V. </p>
 * @author SAMI
 * @version 1.0
 */
public class TOrdenaArbol{
  private Vector vcListado = new Vector();
  private Vector vcArea;
  public Vector getAreaOrdenada(Vector vcAreaTmp){
    vcArea = vcAreaTmp;
    TVDinRep vRow = (TVDinRep) vcArea.get(0);
    ordenaArea(vRow.getInt("ICVEPADRE"));
    return vcListado;
  }

  private void ordenaArea(int iPadre){
    for(int i = 0;i < vcArea.size();i++){
      TVDinRep vArea = (TVDinRep) vcArea.get(i);
      if(vArea.getInt("ICVEPADRE") == iPadre){
        vcListado.add(vArea);
        ordenaArea(vArea.getInt("ICVENODO"));
      }
    }
  }
}
