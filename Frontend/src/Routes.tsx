import { createBrowserRouter, Outlet } from "react-router-dom";
import App from "./App";

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
      element: <></>
    },
    {
      path: "/signin",
      element: <></>
    }
  ]
)