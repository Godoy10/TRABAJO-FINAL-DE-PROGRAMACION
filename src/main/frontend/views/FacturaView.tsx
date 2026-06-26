import { useEffect, useState } from 'react';

import { ComboBox } from '@vaadin/react-components/ComboBox.js';
import { IntegerField } from '@vaadin/react-components/IntegerField.js';
import { Button } from '@vaadin/react-components/Button.js';
import { TerceroService } from "Frontend/generated/endpoints";
import Tercero from "Frontend/generated/com/example/examplefeature/model/Tercero";

export default function FacturaView() {
	//estados donde se almacenan los terceros seleccionados
	const [terceros, setTerceros] = useState<Tercero[]>([]);
	const [terceroSeleccionado, setTerceroSeleccionado]= useState<Tercero| null>(null);
	const [numeroFactura, setNumeroFactura]= useState<string>('');
	
	//
	useEffect(()=>{
		async function cargarTerceros(){
			try{
				const request = {pageNumber: 0, pageSize: 100, sort:{orders: []}};
				const lista = await TerceroService.list(request, undefined);
				setTerceros(lista ?? []);
			} catch (error){
				console.error("Error al cargar los terceros", error);
			}
			
		}
		cargarTerceros();
	}, []);
		return (
		    <div style={{ padding: "2rem", maxWidth: "800px", margin: "0 auto" }}>
		      <h2>Nueva Factura</h2>
			  <hr />
			  
			  
			  {/*Panel de la Cabecera*/}
			  <div style={{display:'flex', gap: '1rem', alignItem: 'baseline', marginBottom: '2rem'}}>
			  
			  <ComboBox 
			   label="Seleccione el Tercero (Cliente)"
			   items={terceros}
			   itemLabelPath="nombre" 
			   selectedItem={terceroSeleccionado}
			   onSelectedItemChanged={(e) => setTerceroSeleccionado(e.detail.value ?? null)}
			   style={{ flexGrow: 2 }}
			   />
			  
			  <IntegerField
			  label="Número de Comprobante"
			  value={numeroFactura}
			  onChange={(e)=> setNumeroFactura(e.target.value)}
			  style={{flexGrow: 1}}
			  />
			  </div>
			  
		      <div style ={{display:'flex', justifyContent: 'flex-end', gap: '1rem'}}>
			  <Button theme="tertiary">Cancelar</Button>
			  <Button theme="primary">Guardar Cabecera</Button>
			  </div>
		    </div>
		  );	
}
 	
 
