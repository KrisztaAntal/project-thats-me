import { CustomFormFieldProps } from '../../Types/MemberTypes';

function CustomFormField({ name, inputId, labelValue, inputType, inputValue, onChange, placeholder, onShowPasswordClick, error }: CustomFormFieldProps) {

  return (
    <div className="w-full">
      <div className="w-full justify-between flex relative gap-2">
        <label htmlFor={inputId}>{labelValue}</label>
        <input className='placeholder:pl-1' name={name} id={inputId} type={inputType} value={inputValue} onChange={onChange} placeholder={placeholder} required />
        {labelValue.includes("Password") &&
          <img className='absolute right-0 top-1/2 transform -translate-y-1/2 cursor-pointer'
            src={inputType === "password" ? "./lock-closed.svg" : "./lock-open.svg"}
            alt={inputType === "password" ? 'show password' : 'hide password'}
            title={inputType === "password" ? 'show' : 'hide'}
            onClick={onShowPasswordClick}></img>}
      </div>
      {error && <p className="w-full text-center text-red-600 text-xs">{error}</p>}
    </div>
  )
}

export default CustomFormField;