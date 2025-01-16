import { createBrowserRouter, Outlet } from "react-router-dom";
import App from "./App";
import Signup from "./Pages/Signup";

export const router = createBrowserRouter(
  [
    {
      path: "/",
      element: < Outlet />,
      children: [
        {
          path: "/default",
          element: <App />
        }
      ]
    },
    {
      path: "/signup",
      element: <Signup />
    },
    {
      path: "/signin",
      element: <></>
    }
  ]
)