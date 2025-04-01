import {useAuth} from "../authProvider/useAuth.tsx";
import {useEffect, useState} from "react";

function MemberProfile() {
    const {member} = useAuth();
    const [avatar, setAvatar] = useState(member?.monogram);


    return (
        <div className={"flex justify-center items-center h-screen"}>
            <div className={"flex flex-col items-center lg:w-3/4 lg:drop-shadow-xl lg:justify-center lg:h-[90vh]"}>
                <div className={"flex h-[40vh] w-full bg-[#D9D9D9] justify-center items-end  lg:bg-white lg:h-2/6 lg:rounded-tl-2xl lg:rounded-tr-2xl"}>
                    {avatar === member?.monogram ?
                        <div style={{color: member?.monogram.colorCode}}
                             className={"rounded-full w-32 h-32 bg-white flex justify-center items-center absolute -mb-6 lg:w-44 lg:h-44 lg:border-2"}>
                            <p className={"font-medium text-6xl lg:text-8xl"}>{member?.monogram.characters}</p>
                        </div>
                        :
                        <div className={"rounded-full w-16 h-16 bg-teal-600"}></div>
                    }
                </div>
                <div className={" lg:flex lg:w-full lg:h-full"}>

                    <div
                        className={"flex flex-col p-8 text-white bg-teal-700 w-screen h-screen lg:w-2/5 lg:h-[66vh] lg:rounded-bl-2xl"}>
                        <div>
                            <p className={"text-xs mb-3"}>Id: {member?.memberId}</p>
                        </div>
                        <div>
                            <p className={"text-xs mb-3"}>Member since: {member?.dateOfRegistry}</p>
                        </div>
                        <div className={"flex justify-between"}>
                            <p className={"text-l"}>Username:</p>
                            <p className={"text-l font-bold mb-2"}>{member?.username}</p>
                        </div>
                        <div className={"flex justify-between"}>
                            <p className={"text-l"}>First name:</p>
                            <p className={"text-l font-bold mb-2"}>{member.firstName}Test</p>
                        </div>
                        <div className={"flex justify-between"}>
                            <p className={"text-l"}>Last name:</p>
                            <p className={"text-l font-bold mb-2"}>{member.lastName}Test</p>
                        </div>
                        <div className={"flex justify-between"}>
                            <p className={"text-l"}>Email:</p>
                            <p className={"text-l font-bold mb-2"}>{member?.email}</p>
                        </div>
                        <div className={"flex justify-between"}>
                            <p className={"text-l"}>Birthdate:</p>
                            <p className={"text-l font-bold mb-2"}>{member?.birthDate.toString()}</p>
                        </div>
                        <div>
                            <p className={"text-l"}>Biography:</p>
                            <p className={"text-l  mb-6 mt-2 h-24 overflow-auto"}>{member.bio} "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."</p>
                        </div>
                        <button
                            className={" btn bg-[#9EF01A] p-2 hover:bg-accent active:bg-success w-1/3"}>Edit
                        </button>
                    </div>
                    <div className={"bg-teal-100  lg:w-3/5 lg:h-[66vh]"}>

                    </div>

                </div>
            </div>
        </div>
    )
}

export default MemberProfile;