const path = require("path");
const HtmlWebpackPlugin = require("html-webpack-plugin");
const { CleanWebpackPlugin } = require("clean-webpack-plugin");
const MiniCssExtractPlugin = require("mini-css-extract-plugin");
const MomentLocalesPlugin = require("moment-locales-webpack-plugin");
module.exports = {
  mode: "development",
  entry: ["./src/index.js"],
  devtool: "source-map",
  devServer: {
    contentBase: path.join(__dirname, "./"), // where dev server will look for static files, not compiled
    publicPath: "/", //relative path to output path where  devserver will look for compiled files
    host: "0.0.0.0",
    port: 3000,
    useLocalIp: true,
    compress: true,
    disableHostCheck: true,
    hot: true,
    hotOnly: true,
    open: true,
    overlay: true,
    stats: {
      colors: true,
      hash: false,
      version: true,
      timings: true,
      cached: false,
      assets: false,
      chunks: false,
      modules: false,
      reasons: false,
      children: false,
      source: false,
      errors: true,
      errorDetails: true,
      warnings: true,
      publicPath: true,
    },
    clientLogLevel: "warning",
    historyApiFallback: true,
  },
  output: {
    filename: "js/[name].bundle.js",
    path: path.resolve(__dirname, "dist"), // base path where to send compiled assets
    publicPath: "/", // base path where referenced files will be look for
  },
  resolve: {
    extensions: ["*", ".js", ".jsx"],
    alias: {
      "@": path.resolve(__dirname, "src"), // shortcut to reference src folder from anywhere
      "react-dom": "@hot-loader/react-dom",
    },
  },
  module: {
    rules: [
      {
        // config for es6 jsx
        test: /\.(js|jsx)$/,
        exclude: /node_modules/,
        use: {
          loader: "babel-loader",
        },
      },
      {
        // config for sass compilation
        test: /\.scss$/,
        use: [
          {
            loader: MiniCssExtractPlugin.loader,
          },
          "css-loader",
          {
            loader: "sass-loader",
          },
        ],
      },
      {
        test: /\.(jpg|jpeg|png|svg|woff|eot|ttf|otf|pdf)$/,
        use: [
          {
            loader: "file-loader",
            options: {
              name: "[name][hash].[ext]",
              outputPath: "images",
              esModule: false,
            },
          },
        ],
      },
      {
        test: /\.css$/,
        use: [
          {
            loader: MiniCssExtractPlugin.loader,
          },
          "css-loader",
        ],
      },
      // {
      //   // config for fonts
      //   test: /\.(woff|woff2|eot|ttf|otf)$/,
      //   use: {
      //     loader: "url-loader",
      //   },
      // },
    ],
  },
  plugins: [
    // Bỏ hết tất cả định dạng, chỉ chừa lại 'en'
    new MomentLocalesPlugin(),
    // Hoặc bỏ hết tất cả chỉ chừa lại 'en' và 'vi'
    // Bạn không thể bỏ 'en' vì đó là mặc định của moment.js
    new MomentLocalesPlugin({
      localesToKeep: ["vi"],
    }),
    new CleanWebpackPlugin(),
    new HtmlWebpackPlugin({
      template: "./public/index.html",
      filename: "./index.html",
      favicon: "./public/favicon.ico",
    }),
    new MiniCssExtractPlugin({
      // plugin for controlling how compiled css will be outputted and named
      filename: "css/[name].css",
      chunkFilename: "css/[id].css",
    }),
  ],
};
