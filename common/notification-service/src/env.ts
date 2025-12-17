import dotenv from "dotenv"
import path from "node:path"

dotenv.config({
    path: path.resolve(process.env.ENV_FILE ?? "config/.env")
})