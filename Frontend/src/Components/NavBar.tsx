import {useAuth} from "../authProvider/useAuth.tsx";

function NavBar() {
    const {logout} = useAuth()

    function handleLogout() {
        logout();
    }

    return (
        <div>
            <div className="dropdown dropdown-end fixed top-0 right-0">
                <div tabIndex={0} role="button" className="btn m-1">☰</div>
                <ul tabIndex={0} className="dropdown-content menu bg-teal-400 rounded-box z-1 w-52 p-2 shadow-sm">
                    <li><a href={"main"}>Main</a></li>
                    <li><a href={"profile"}>Profile</a></li>
                    <li onClick={handleLogout}><a href={"/login"}>Logout</a></li>
                </ul>
            </div>

        </div>
    )
}

export default NavBar;