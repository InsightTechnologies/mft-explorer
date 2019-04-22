
import express from 'express'
import cors from 'cors'
import React from 'react'
import ReactDOMServer from 'react-dom/server'
import {StaticRouter } from 'react-router'
import bodyParser from 'body-parser'
import App from './src/App'
import theme from './src/theme'
import { SheetsRegistry } from 'jss'
import JssProvider from 'react-jss/lib/JssProvider'
import { MuiThemeProvider,  createGenerateClassName} from '@material-ui/core/styles'


function handleRender(req, res) {

    // create a context object for passing while  routing
    const context = {};

    // Create a sheetsRegistry instance.
    const sheetsRegistry = new SheetsRegistry();

    // Create a sheetsManager instance.
    const sheetsManager = new Map();

    // Create a new class name generator.
    const generateClassName = createGenerateClassName();

    // Render the component to a string.
    const html = ReactDOMServer.renderToString(
        <JssProvider registry={sheetsRegistry} generateClassName={generateClassName}>
            <MuiThemeProvider theme={theme} sheetsManager={sheetsManager}>
                <StaticRouter location={req.url} context={context}>
                    <App />
                </StaticRouter>
            </MuiThemeProvider>
        </JssProvider>
    )

    // Grab the CSS from our sheetsRegistry.
    const css = sheetsRegistry.toString()

    // Send the rendered page back to the client.
    res.send(renderFullPage(html, css))
}

function renderFullPage(html, css) {
    return `
    <!doctype html>
    <html>
        <head>
            <title>MFT Explorer</title>
            <style id="jss-server-side">${css}</style>
        </head>
        <body style='margin:0; border:0; padding:0;'>
            <div id="root">${html}</div>
            <script src="client_bundle.js"></script>
        </body>
    </html>
    `;
}

const app = express()
const PORT = process.env.PORT || 3000
app.use(cors())
app.use(bodyParser.json())
app.use(express.static('build/public'))

app.get('/*',handleRender)

app.listen(PORT, () => {
    console.log(`App running on ${PORT}`)
})
