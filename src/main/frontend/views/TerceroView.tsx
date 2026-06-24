import { AutoCrud } from "@vaadin/hilla-react-crud";
import { TerceroService } from "Frontend/generated/endpoints";
import TerceroModel from "Frontend/generated/com/example/examplefeature/model/TerceroModel";

export default function TerceroView() {
  return (
    <div style={{ padding: "1rem" }}>
      <h2>Gestión de Terceros</h2>
      <AutoCrud service={TerceroService} model={TerceroModel} />
    </div>
  );
}
