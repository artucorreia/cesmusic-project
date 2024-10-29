'use client'

import style from '../styles/credentialsPage.module.css'
import buttonStyle from '../styles/button.module.css'

import { useRouter } from 'next/navigation'

interface CredentialsProps {
    isRegistering: boolean
}

export default function CredentialsPage({isRegistering}: CredentialsProps) {

    const router = useRouter()

    const onHandleRouter = () => {
        if(isRegistering) {
            router.push('/login')
        } else {
            router.push('/register')
        }
    }

    return(
        <div className={`${style.container}`}>

            <h1 
                className='text-center text-white text-2xl'>{isRegistering ? 'Faça já seu registro' : 'Entre no CesMusic'}
            </h1>

            <form
                onSubmit={(ev) => {ev.preventDefault()
                    
                }}>
            
                {isRegistering
                    ?
                        <input 
                            placeholder='Digite seu nome'
                            type="string" 
                            name="name" 
                            id="name" 
                            className={`${style.input_style}`}
                        />
                    :
                        null}

                <input 
                    placeholder='Digite seu email'
                    type="email" 
                    name="email" 
                    id="email" 
                    className={`${style.input_style}`}
                />

                <input 
                    placeholder='Digite sua senha'
                    type="password" 
                    name="password" 
                    id="password" 
                    className={`${style.input_style}`}
                />

                {isRegistering
                    ?
                        <input 
                            placeholder='Confirme sua senha'
                            type="password" 
                            name="password" 
                            id="password" 
                            className={`${style.input_style}`}
                        />
                    :
                        null
                }

                    <button 
                        type="submit"
                        className={`${buttonStyle.submit_button}`}> {isRegistering ? "Registrar" : "Login"}
                    </button>

                    {isRegistering 
                        ?   
                            <div>
                                <p className='text-white'>Já é registrado? </p>
                                <p onClick={onHandleRouter} className='text-white underline hover:cursor-pointer hover:text-blue-400'>CLIQUE AQUI para fazer o login</p>
                            </div>
                        :
                        <div>
                            <p className='text-white'>Ainda não tem cadastro? </p>
                            <p onClick={onHandleRouter} className='text-white underline hover:cursor-pointer hover:text-blue-400'>CLIQUE AQUI para se cadastrar</p>
                        </div>
                    }  

            </form>
            
        </div>
    )
}