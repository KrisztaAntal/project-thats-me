import {createBrowserRouter} from "react-router-dom";
import App from "./App";
import Signup from "./Pages/Signup";
import Layout from "./Layout.tsx";
import Login from "./Pages/Login.tsx";
import MainPage from "./Pages/MainPage.tsx";

export const router = createBrowserRouter(
    [
        {
            path: "/",
            element: < Layout/>,
            children: [
                {
                    path: "default",
                    element: <App/>
                },
                {
                    path:"main",
                    element: <MainPage/>
                }
            ]
        },
        {
            path: "/signup",
            element: <Signup/>
        },
        {
            path: "/login",
            element: <Login/>
        }
    ]
)