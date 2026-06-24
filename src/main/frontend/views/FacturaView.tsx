import { AutoCrud } from "@vaadin/hilla-react-crud";
import { FacturaService } from "Frontend/generated/endpoints";
import FacturaModel from "Frontend/generated/com/example/examplefeature/model/FacturaModel";

export default function FacturaView() {
  return (
    <div style={{ padding: "1rem" }}>
      <h2>Gestión de Terceros</h2>
	  <h4>C.R.U.D.</h4>
	  <br/>
      <AutoCrud service={FacturaService} model={FacturaModel} 
	  className="Factura"/>
    </div>
  );
}
