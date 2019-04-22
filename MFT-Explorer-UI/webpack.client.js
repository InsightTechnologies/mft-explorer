const path = require('path')

module.exports = {
    entry: './client.js',
    mode: process.env.NODE_ENV || 'development',
    output: {
        filename: 'client_bundle.js',
        path: path.resolve(__dirname,'build/public'),
        publicPath: '/build/public'
    },
    module: {
        rules: [ {
            test: /\.js$/, 
            loader: 'babel-loader', 
            exclude: /node_modules/,
            options: {
                presets: ['env','react','stage-2']
            }
        }]
    }
}