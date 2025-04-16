import {useAuth} from "../authProvider/useAuth.tsx";

function MainPage() {
    const {member} = useAuth();
    console.log(member)




    return (<div className={"grid justify-center h-full w-full"}>
        <h1 className={"text-6xl text-teal-950 font-extrabold"}>Welcome{member}!</h1>
    </div>)
}

export default MainPage;