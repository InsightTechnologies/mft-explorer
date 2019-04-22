import React from 'react'
import ReactDOM from 'react-dom'
import { BrowserRouter } from 'react-router-dom'
import JssProvider from 'react-jss/lib/JssProvider';
import App from './src/App'
import theme from './src/theme'
import { MuiThemeProvider,  createGenerateClassName} from '@material-ui/core/styles';
import { SheetsRegistry } from 'jss'

class Client extends React.Component {
    // // Remove the server-side injected CSS.
    // componentDidMount() {
    //     const jssStyles = document.getElementById('jss-server-side');
    //     if (jssStyles && jssStyles.parentNode) {
    //         jssStyles.parentNode.removeChild(jssStyles);
    //     }
    // }  
    render() {
        return <App />
    }
}
const sheetsRegistry = new SheetsRegistry();    
const sheetsManager = new Map();


const generateClassName = createGenerateClassName()

ReactDOM.hydrate(
    <JssProvider registry={sheetsRegistry} generateClassName={generateClassName}>
        <MuiThemeProvider theme={theme} sheetsManager={sheetsManager}>
            <BrowserRouter>
            <Client />
            </BrowserRouter>
        </MuiThemeProvider>
    </JssProvider>,
    document.querySelector('#root'),
    // () => {
    //     document.getElementById('jss-server-side').remove()
    // }
)