function MemberProfileBasicDataList({member, memberByUsername}) {
    return (
        <div
            className={"flex flex-col p-8 text-white bg-teal-700 w-screen h-screen lg:w-2/5 lg:h-[66vh] lg:rounded-bl-2xl"}>
            <div>
                <p className={"text-xs mb-3"}>Id: {memberByUsername?.memberId}</p>
            </div>
            <div>
                <p className={"text-xs mb-3"}>Member since: {memberByUsername?.dateOfRegistry}</p>
            </div>
            <div className={"flex justify-between"}>
                <p className={"text-l"}>Username:</p>
                <p className={"text-l font-bold mb-2"}>{memberByUsername?.username}</p>
            </div>
            <div className={"flex justify-between"}>
                <p className={"text-l"}>First name:</p>
                <p className={"text-l font-bold mb-2"}>{memberByUsername?.firstName}Test</p>
            </div>
            <div className={"flex justify-between"}>
                <p className={"text-l"}>Last name:</p>
                <p className={"text-l font-bold mb-2"}>{memberByUsername?.lastName}Test</p>
            </div>
            <div className={"flex justify-between"}>
                <p className={"text-l"}>Email:</p>
                <p className={"text-l font-bold mb-2"}>{memberByUsername?.email}</p>
            </div>
            <div className={"flex justify-between"}>
                <p className={"text-l"}>Birthdate:</p>
                <p className={"text-l font-bold mb-2"}>{memberByUsername?.birthDate.toString()}</p>
            </div>
            <div>
                <p className={"text-l"}>Biography:</p>
                <p className={"text-l  mb-6 mt-2 h-24 overflow-auto"}>{memberByUsername?.bio} "Lorem ipsum
                    dolor sit
                    amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore
                    magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut
                    aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate
                    velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non
                    proident, sunt in culpa qui officia deserunt mollit anim id est laborum."</p>
            </div>
            {memberByUsername?.username === member?.username &&
                <button
                    className={" btn bg-[#9EF01A] p-2 hover:bg-accent active:bg-success w-1/3"}>Edit
                </button>
            }
        </div>
    )
}

export default MemberProfileBasicDataList;