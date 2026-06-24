import { AutoCrud } from "@vaadin/hilla-react-crud";
import { FacultadService } from "Frontend/generated/endpoints";
import FacultadModel from "Frontend/generated/com/example/examplefeature/model/FacultadModel";

export default function FacultadView() {
  return (
    <div style={{ padding: "1rem" }}>
      <h2>Gestión de Facultades</h2>
	  <h4>C.R.U.D.</h4>
	  <br/>
      <AutoCrud service={FacultadService} model={FacultadModel} />
    </div>
  );
}
