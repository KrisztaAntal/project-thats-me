import {useAuth} from "../authProvider/useAuth.tsx";

function MemberProfile() {
    const {member} = useAuth();
    return (

        <div className={"flex flex-col justify-center"}>

            <div className={"rounded-full w-16 h-16 bg-teal-600"}></div>
            <div className={"flex flex-col justify-center p-5 "}>
                <p className={"text-xs mb-1"}>Id: {member?.memberId}</p>
                <p className={"text-xs mb-3"}>Member since: {member?.dateOfRegistry}</p>
                <div>

                    <p className={"text-xs"}>Username:</p>
                    <p className={"text-l font-bold mb-2"}>{member?.username}</p>
                </div>
                {member?.firstName &&
                    <div>
                        <p className={"text-xs"}>First name:</p>
                        <p className={"text-l font-bold mb-2"}>{member.firstName}</p>
                    </div>
                }
                {member?.lastName &&
                    <div>
                        <p className={"text-xs"}>Last name:</p>
                        <p className={"text-l font-bold mb-2"}>{member.lastName}</p>
                    </div>
                }
                <div>
                    <p className={"text-xs"}>Email:</p>
                    <p className={"text-l font-bold mb-2"}>{member?.email}</p>
                </div>
                <div>
                    <p className={"text-xs"}>Birthdate:</p>
                    <p className={"text-l font-bold mb-2"}>{member?.birthDate.toString()}</p>
                </div>
                {member?.bio &&
                    <div>
                        <p className={"text-xs"}>Biography:</p>
                        <p className={"text-l font-bold mb-2"}>{member.bio}</p>
                    </div>
                }
            </div>
        </div>

    )
}

export default MemberProfile;