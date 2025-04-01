import {Outlet} from "react-router-dom";
import NavBar from "./Components/NavBar.tsx";

function Layout(){
    return (
        <div className="min-h-screen">
            <NavBar/>
            <Outlet/>
        </div>
    )
}
export default Layout;