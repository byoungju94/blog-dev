import ReactDOM from 'react-dom';
import BodyComponents from './src/BodyComponents';

const rootElement = document.getElementById("root");
if (rootElement !== null) {
    const bodyComponents = new BodyComponents({id: "awsc", name: "java"});
    ReactDOM.render(bodyComponents.render(), rootElement);
}
